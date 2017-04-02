package com.taotao.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisService {

    //required =false 要用到时才注册
    @Autowired(required =false )
    private ShardedJedisPool shardedJedisPool;

    public <T> T excute(Function<ShardedJedis, T> fun) {
        ShardedJedis shardedJedis = null;
        try {
            // 从连接池中获取到jedis分片对象
            shardedJedis = shardedJedisPool.getResource();

            return fun.callback(shardedJedis);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != shardedJedis) {
                // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
                shardedJedis.close();
            }
        }
        // 关闭连接池
        shardedJedisPool.close();

        return null;
    }

    /**
     * 设值
     * 
     * @param key
     * @param value
     * @return
     */
    public String set(final String key, final String value) {

        return this.excute(new Function<ShardedJedis, String>() {
            @Override
            public String callback(ShardedJedis shardedJedis) {
                return shardedJedis.set(key, value);
            }
        });
    }

    /**
     * 拿值
     * 
     * @param key
     * @return
     */
    public String get(final String key) {

        return this.excute(new Function<ShardedJedis, String>() {
            @Override
            public String callback(ShardedJedis shardedJedis) {
                return shardedJedis.get(key);
            }
        });
    }

    /**
     * 设值并指定生存时间
     */
    public String set(final String key, final String value, final Integer seconds) {

        return this.excute(new Function<ShardedJedis, String>() {
            @Override
            public String callback(ShardedJedis shardedJedis) {
                String str = shardedJedis.set(key, value);
                shardedJedis.expire(key, seconds);
                return str;
            }
        });
    }
    /**
     * 单独为key，设置生存时间
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(final String key,final Integer seconds){

        return this.excute(new Function<ShardedJedis, Long>() {
            @Override
            public Long callback(ShardedJedis shardedJedis) {
                shardedJedis.expire(key, seconds);
                return  shardedJedis.expire(key, seconds);
            }
        });
    }
    /**
     * 删除一个键
     * @param key
     * @return
     */
    public Long del(final String key){
        return this.excute(new Function<ShardedJedis, Long>() {
            @Override
            public Long callback(ShardedJedis shardedJedis) {
                return  shardedJedis.del(key);
            }
        });
    }
}
