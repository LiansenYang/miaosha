package com.yangls.miaosha.controller;

import com.yangls.miaosha.annotation.NeedLogin;
import com.yangls.miaosha.common.Constants;
import com.yangls.miaosha.model.MiaoshaUser;
import com.yangls.miaosha.redis.GoodsKey;
import com.yangls.miaosha.redis.MiaoshaUserKey;
import com.yangls.miaosha.redis.RedisService;
import com.yangls.miaosha.service.GoodsService;
import com.yangls.miaosha.service.impl.UserServiceImpl;
import com.yangls.miaosha.vo.GoodsDetailVo;
import com.yangls.miaosha.vo.GoodsVo;
import com.yangls.miaosha.web.result.ResponseObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Set;

/**
 * @description: 商品controller
 * @author: yangLs
 * @create: 2020-06-02 21:12
 **/
@Controller
@RequestMapping("/goods")
public class goodsController {
//    @Autowired
//    RedisService redisService;

    @Autowired
    GoodsService goodsService;
    @Autowired
    RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

//    @RequestMapping("/to_list")
//    public String list(Model model, @CookieValue(Constants.COOKIE_NAME_TOKEN) String token) {
//        MiaoshaUser miaoshaUser = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
//        model.addAttribute("user", miaoshaUser);
//        return "goods_list";
//    }
    //修改为使用UserMethodArgumentResolver封装user
    @RequestMapping("/to_list2")
//    @NeedLogin
    public String list(Model model, MiaoshaUser user) {
        model.addAttribute("user", user);
        return "goods_list";
    }

    @RequestMapping("/to_listNo_cache")
    public String list(Model model) {
        model.addAttribute("goodsList", goodsService.getMiaoshaGoods());
        return "goods_list";
    }
    //页面级别redis缓存,produces为controller返回的类型
    @RequestMapping(value = "/to_list",produces = "text/html")
    @ResponseBody
    public String listCache(Model model, HttpServletRequest request, HttpServletResponse response) {
        //判断redis缓存中是否有存在
        String html = redisService.get(GoodsKey.getGoodsList,"",String.class);
        if(StringUtils.isNotEmpty(html)){
            return html;
        }
        //thymeleaf手动渲染
        model.addAttribute("goodsList", goodsService.getMiaoshaGoods());
        WebContext webContext = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list",webContext);
        //保存页面进缓存中
        if(StringUtils.isNotEmpty(html)){
            redisService.set(GoodsKey.getGoodsList,"",html);
        }
        return html;
    }

    @RequestMapping("/to_detailNo_cache/{goodsId}")
    //PathVariable（）内的值对应path{}中的值
    public String to_detailNo_cache(Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user",user);
        //获取商品的信息
        GoodsVo goodsVo = goodsService.getMiaoshaGoodsByGoodsId(goodsId);
        model.addAttribute("goods", goodsVo);

        //获取商品此时的状态和需要倒计时的时间
        long startT = goodsVo.getStartDate().getTime();
        long endT = goodsVo.getEndDate().getTime();
        long curntT = System.currentTimeMillis();

        int miaoshaStatus =Constants.MIAOSHA_STATUS_UNSTART ;
        int remainSeconds = 0; //倒计时
        if(startT > curntT){ //未开始
            miaoshaStatus = Constants.MIAOSHA_STATUS_UNSTART;
            remainSeconds = (int)((startT-curntT)/1000);
        }else if(curntT > endT){ //已结束
            miaoshaStatus = Constants.MIAOSHA_STATUS_STARTED;
            remainSeconds = -1;
        }else{//进行中
            miaoshaStatus = Constants.MIAOSHA_STATUS_STARTING;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);

        return "goods_detail";
    }

    @RequestMapping(value = "/to_detail_cache/{goodsId}",produces = "text/html")
    //PathVariable（）内的值对应path{}中的值
    @ResponseBody
    public String to_detail_cache(HttpServletRequest request, HttpServletResponse response,Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user",user);
        //从缓存中获取页面
        String html =redisService.get(GoodsKey.getGoodsDetail,""+goodsId,String.class);

        if(StringUtils.isNotEmpty(html)){
            return html;
        }
        //获取商品的信息
        GoodsVo goodsVo = goodsService.getMiaoshaGoodsByGoodsId(goodsId);
        model.addAttribute("goods", goodsVo);

        //获取商品此时的状态和需要倒计时的时间
        long startT = goodsVo.getStartDate().getTime();
        long endT = goodsVo.getEndDate().getTime();
        long curntT = System.currentTimeMillis();

        int miaoshaStatus =Constants.MIAOSHA_STATUS_UNSTART ;
        int remainSeconds = 0; //倒计时
        if(startT > curntT){ //未开始
            miaoshaStatus = Constants.MIAOSHA_STATUS_UNSTART;
            remainSeconds = (int)((startT-curntT)/1000);
        }else if(curntT > endT){ //已结束
            miaoshaStatus = Constants.MIAOSHA_STATUS_STARTED;
            remainSeconds = -1;
        }else{//进行中
            miaoshaStatus = Constants.MIAOSHA_STATUS_STARTING;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);

        //手动渲染
        WebContext webContext = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail",webContext);
        //保存页面进缓存中,这个功能因为有日期需要实时的，所以这里使用缓存不适合，但是仅仅用于做和没用缓存的压测对比
        if(StringUtils.isNotEmpty(html)){
            redisService.set(GoodsKey.getGoodsDetail,""+goodsId,html);
        }

        return html;
    }

    //动静分离，这里只用来做返回数据
    @RequestMapping(value = "/to_detail/{goodsId}")
    //PathVariable（）内的值对应path{}中的值
    @ResponseBody
    public ResponseObject<GoodsDetailVo> to_detail(MiaoshaUser user,@PathVariable("goodsId") long goodsId) {

        //获取商品的信息
        GoodsVo goodsVo = goodsService.getMiaoshaGoodsByGoodsId(goodsId);

        //获取商品此时的状态和需要倒计时的时间
        long startT = goodsVo.getStartDate().getTime();
        long endT = goodsVo.getEndDate().getTime();
        long curntT = System.currentTimeMillis();

        int miaoshaStatus =Constants.MIAOSHA_STATUS_UNSTART ;
        int remainSeconds = 0; //倒计时
        if(startT > curntT){ //未开始
            miaoshaStatus = Constants.MIAOSHA_STATUS_UNSTART;
            remainSeconds = (int)((startT-curntT)/1000);
        }else if(curntT > endT){ //已结束
            miaoshaStatus = Constants.MIAOSHA_STATUS_STARTED;
            remainSeconds = -1;
        }else{//进行中
            miaoshaStatus = Constants.MIAOSHA_STATUS_STARTING;
            remainSeconds = 0;
        }
        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        goodsDetailVo.setGoods(goodsVo);
        goodsDetailVo.setUser(user);
        goodsDetailVo.setMiaoshaStatus(miaoshaStatus);
        goodsDetailVo.setRemainSeconds(remainSeconds);
        return new ResponseObject<GoodsDetailVo>(goodsDetailVo);
    }

}
