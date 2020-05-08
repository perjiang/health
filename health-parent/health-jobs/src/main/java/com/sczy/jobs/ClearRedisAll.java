package com.sczy.jobs;

import com.sczy.constant.RedisConstant;
import com.sczy.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearRedisAll {
    @Autowired
    private JedisPool jedisPool;
    public void ClaarAll(){
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (set != null && set.size() > 0){
            for (String picName : set) {
                QiniuUtils.deleteFileFromQiniu(picName);
                //从Redis集合中删除图片名称
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,picName);
                System.out.println("自定义任务执行，清理垃圾图片:" + picName);
            }
        }
        Set<String> set1 = jedisPool.getResource().smembers(RedisConstant.SETMEAL_PIC_RESOURCES);
        Set<String> set2 = jedisPool.getResource().smembers(RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (set1 != null){
            for (String s : set1) {
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,s);
            }
        }
        if (set2 != null){
            for (String s : set2) {
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES,s);
            }
        }
    }
}
