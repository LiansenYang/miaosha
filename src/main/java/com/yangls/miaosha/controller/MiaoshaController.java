package com.yangls.miaosha.controller;

import com.sun.istack.internal.Interned;
import com.yangls.miaosha.annotation.NeedLogin;
import com.yangls.miaosha.exception.GlobalException;
import com.yangls.miaosha.model.MiaoshaOrder;
import com.yangls.miaosha.model.MiaoshaUser;
import com.yangls.miaosha.model.OrderInfo;
import com.yangls.miaosha.rabbitMq.MQSender;
import com.yangls.miaosha.rabbitMq.MiaoshaMessage;
import com.yangls.miaosha.redis.GoodsKey;
import com.yangls.miaosha.redis.MiaoshaKey;
import com.yangls.miaosha.redis.OrderKey;
import com.yangls.miaosha.redis.RedisService;
import com.yangls.miaosha.service.GoodsService;
import com.yangls.miaosha.service.MiaoshaService;
import com.yangls.miaosha.service.OrderService;
import com.yangls.miaosha.vo.GoodsVo;
import com.yangls.miaosha.web.result.ResponseObject;
import com.yangls.miaosha.web.result.ResponseStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @description: 秒杀Controller
 * @author: yangLs
 * @create: 2020-06-03 19:52
 **/
@Controller
@RequestMapping("miaosha")
//InitializingBean，在实例化bean之后做的afterPropertiesSet，可以把一下预先要加载如缓存中的数据加载进缓存,仅限于单机模式，
//其实更应该设定一个管理段配置秒杀商品的信息，保证从一个地方修改redis
public class MiaoshaController implements InitializingBean{


    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService miaoshaOrderService;

    @Autowired
    MiaoshaService miaoshaService;
    @Autowired
    RedisService redisService;

    @Autowired
    MQSender mqSender;

    private HashMap<Long, Boolean> localOverMap =  new HashMap<Long, Boolean>();

    /**
     * 系统初始化
     * */
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.getMiaoshaGoods();
        if(goodsList == null) {
            return;
        }
        for(GoodsVo goods : goodsList) {
            redisService.set(GoodsKey.getMiaoshaGoodsStock, ""+goods.getId(), goods.getStockCount());
            localOverMap.put(goods.getId(), false);
        }
    }

    @RequestMapping("do_miaosha4webSocket")
    @ResponseBody
    @NeedLogin
    public ResponseObject<MiaoshaUser> do_miaosha4webSocket(@RequestParam("goodsId") long goodsId, MiaoshaUser user, Model model){
        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if(over) {
            return new ResponseObject(ResponseStatus.MIAO_SHA_OVER);
        }
        //预减库存，在实例化bean的时候已经把库存放入到内存
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, ""+goodsId);//10
        if(stock < 0) { //如果少于0就是卖完了
            localOverMap.put(goodsId, true);
            redisService.set(MiaoshaKey.isGoodsOver,""+goodsId,true);
            return new ResponseObject(ResponseStatus.MIAO_SHA_OVER);
        }

        //查询秒杀订单中是否有这个用户之前已经秒杀过了
        MiaoshaOrder miaoshaOrder = miaoshaOrderService.getOrderByUserIdAndGoodsId(user.getId(),goodsId);
        if(miaoshaOrder != null){
            return new ResponseObject(ResponseStatus.REPEATE_MIAOSHA);
        }
        //入队
        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setUser(user);
        mm.setGoodsId(goodsId);
        mqSender.sendMiaoshaMessage(mm);
        return new ResponseObject<MiaoshaUser>(user); //排队中

    }


    @RequestMapping("do_miaosha")
    @ResponseBody
    @NeedLogin
    public ResponseObject<Integer> maiosha(@RequestParam("goodsId") long goodsId, MiaoshaUser user, Model model){
        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if(over) {
            return new ResponseObject(ResponseStatus.MIAO_SHA_OVER);
        }
        //预减库存，在实例化bean的时候已经把库存放入到内存
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, ""+goodsId);//10
        if(stock < 0) { //如果少于0就是卖完了
            localOverMap.put(goodsId, true);
            redisService.set(MiaoshaKey.isGoodsOver,""+goodsId,true);
            return new ResponseObject(ResponseStatus.MIAO_SHA_OVER);
        }

        //查询秒杀订单中是否有这个用户之前已经秒杀过了
        MiaoshaOrder miaoshaOrder = miaoshaOrderService.getOrderByUserIdAndGoodsId(user.getId(),goodsId);
        if(miaoshaOrder != null){
            return new ResponseObject(ResponseStatus.REPEATE_MIAOSHA);
        }
        //入队
        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setUser(user);
        mm.setGoodsId(goodsId);
        mqSender.sendMiaoshaMessage(mm);
        return new ResponseObject<Integer>(0); //排队中

    }

    @RequestMapping("do_miaoshaNo_RabbitMq")
    @ResponseBody
    @NeedLogin
    public ResponseObject<OrderInfo> maioshaNO_rabbitMq(@RequestParam("goodsId") long goodsId, MiaoshaUser user, Model model){
        model.addAttribute("user", user);
        //加了NeedLogin拦截器，不用判断用户是否为空，直接在拦截器中跳转到登录页面
//        if(user == null) {
//            return "login";
//        }

        //判断库存
        GoodsVo goodsVo = goodsService.getMiaoshaGoodsByGoodsId(goodsId);

        if(goodsVo.getStockCount() <=0 ){//已经没有了，跳转到已经秒杀没有了的页面
//            model.addAttribute("errmsg", ResponseStatus.MIAO_SHA_OVER.getStatusMsg());
//            return "miaosha_fail";
            return new ResponseObject(ResponseStatus.MIAO_SHA_OVER);
        }

        //查询秒杀订单中是否有这个用户之前已经秒杀过了
        MiaoshaOrder miaoshaOrder = miaoshaOrderService.getOrderByUserIdAndGoodsId(user.getId(),goodsId);
        if(miaoshaOrder != null){
//            model.addAttribute("errmsg", ResponseStatus.REPEATE_MIAOSHA.getStatusMsg());
//            return "miaosha_fail";
            return new ResponseObject(ResponseStatus.REPEATE_MIAOSHA);
        }

        //减库存，下订单 写入秒杀订单
        OrderInfo orderInfo = null;
        try {
            orderInfo = miaoshaService.miaosha(user,goodsVo);
        } catch (GlobalException e) {
            e.printStackTrace();
            return new ResponseObject(e.getResponseStatus());
        }


//        model.addAttribute("orderInfo", orderInfo);
//        model.addAttribute("goods", goodsVo);
//
//        return "order_detail";
        return new ResponseObject<OrderInfo>(orderInfo);
    }


    /**
     * 恢复数据，重新测试
     * @param model
     * @return
     */
    @RequestMapping(value="/reset", method= RequestMethod.GET)
    @ResponseBody
    public ResponseObject<Boolean> reset(Model model) {
        List<GoodsVo> goodsList = goodsService.getMiaoshaGoods();
        for(GoodsVo goods : goodsList) {
            goods.setStockCount(10);
            redisService.set(GoodsKey.getMiaoshaGoodsStock, ""+goods.getId(), 10);
            localOverMap.put(goods.getId(), false);
        }
        redisService.delete(OrderKey.getMiaoshaOrderByUidGid);
        redisService.delete(MiaoshaKey.isGoodsOver);
        miaoshaService.reset(goodsList);
        return new ResponseObject<Boolean>(true);
    }


    /**
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     * */
    @RequestMapping(value="/result", method=RequestMethod.GET)
    @ResponseBody
    @NeedLogin
    public ResponseObject<Long> miaoshaResult(Model model,MiaoshaUser user,
                                      @RequestParam("goodsId")long goodsId) {
        long result  =miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return new ResponseObject<Long>(result);
    }
}
