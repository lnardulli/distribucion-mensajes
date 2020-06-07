package com.test.masvoz.cache;

import com.test.masvoz.model.dto.ProviderDto;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.PriorityQueue;

@Repository
public class RedisRepository {

    private HashOperations<String, String, Map<String, PriorityQueue<ProviderDto>>> hashOperations;

    private RedisTemplate redisTemplate;

    static String PROVIDER_KEY = "PROVIDER";

    public RedisRepository(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    public void saveProviders(Map<String, PriorityQueue<ProviderDto>> provider){
        hashOperations.put(PROVIDER_KEY, PROVIDER_KEY, provider);
    }

    public Map<String, PriorityQueue<ProviderDto>> getProvider(){
        return hashOperations.get(PROVIDER_KEY, PROVIDER_KEY);
    }


}
