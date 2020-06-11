package com.yangls.miaosha.rabbitMq;

import com.yangls.miaosha.common.CommonUtils;
import com.yangls.miaosha.common.Constants;
import com.yangls.miaosha.exception.GlobalException;
import com.yangls.miaosha.model.MiaoshaOrder;
import com.yangls.miaosha.model.MiaoshaUser;
import com.yangls.miaosha.model.OrderInfo;
import com.yangls.miaosha.redis.RedisService;
import com.yangls.miaosha.service.GoodsService;
import com.yangls.miaosha.service.MiaoshaService;
import com.yangls.miaosha.service.OrderService;
import com.yangls.miaosha.vo.GoodsVo;
import com.yangls.miaosha.websocket.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @description: 消息接收者
 * @author: yangLs
 * @create: 2020-06-08 17:54
 **/
@Service
public class MQReceiver {
    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;


    @RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
    public void receive(String message) {
        log.info("receive message:"+message);
        MiaoshaMessage mm  = CommonUtils.stringToBean(message, MiaoshaMessage.class);
        MiaoshaUser user = mm.getUser();
        long goodsId = mm.getGoodsId();

        GoodsVo goods = goodsService.getMiaoshaGoodsByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if(order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        try {
            OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
            //通过webSocket 发送信息
            WebSocketServer.sendInfo(String.valueOf(orderInfo.getId()),String.valueOf(user.getId()));
        } catch (GlobalException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @RabbitListener(queues=MQConfig.QUEUE)
    public void receiveQueue(String message) {
        log.info("receive message:"+message);
    }

    @RabbitListener(queues=MQConfig.EXCHAGE_QUEUE1)
    public void receiveTopic1(String message) {
        log.info(" exchage  queue1 message:"+message);
    }

    @RabbitListener(queues=MQConfig.EXCHAGE_QUEUE2)
    public void receiveTopic2(String message) {
        log.info(" exchage  queue2 message:"+message);
    }

    @RabbitListener(queues=MQConfig.HEADER_QUEUE)
    public void receiveHeaderQueue(byte[] message) {
        log.info(" header  queue message:"+new String(message));
    }

}
