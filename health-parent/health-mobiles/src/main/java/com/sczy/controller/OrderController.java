package com.sczy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sczy.constant.MessageConstant;
import com.sczy.constant.RedisMessageConstant;
import com.sczy.entity.Result;
import com.sczy.pojo.Order;
import com.sczy.service.OrderService;
import com.sczy.utils.SMSUtils;
import com.sczy.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * 体检预约处理
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){
        String telephone = (String) map.get("telephone");
        String validateCodeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);//获取redis中的验证码
        String validateCode = (String) map.get("validateCode");//获取表单提交的验证码
        //验证码进行比对
        if (validateCodeInRedis != null && validateCode != null && validateCode.equals(validateCodeInRedis)){
            //如果比对成功
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            Result result = new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
            try {
                 result = orderService.order(map);
            }catch (Exception e){
                e.printStackTrace();
                return result;
            }if(result.isFlag()){
                //预约成功，可以为用户发送短信
                try{
                    SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone, ValidateCodeUtils.generateValidateCode(6).toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return result;
        }else {
            //比对不成功
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
            try{
                Map map = orderService.findById(id);
                return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
            }catch (Exception e){
                e.printStackTrace();
                return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
            }

    }
}
