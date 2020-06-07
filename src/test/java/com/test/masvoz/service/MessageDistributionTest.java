package com.test.masvoz.service;

import com.test.masvoz.cache.RedisRepository;
import com.test.masvoz.exceptions.PrefixNotFoundException;
import com.test.masvoz.model.dto.ProviderDto;
import com.test.masvoz.model.response.ProviderResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageDistributionTest {

    @Autowired
    MessageDistribution messageService;

    @MockBean
    RedisRepository redisRepository;

    Map<String, PriorityQueue<ProviderDto>> mapProviders = new HashMap<>();

    @Before
    public void setUp() {

        PriorityQueue<ProviderDto> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(ProviderDto.builder().coste(1).proveedor("P1").sentMessages(0).build());

        mapProviders.put("0034", priorityQueue);
    }

    @Test
    public void shouldReturnP1WhenProcessNumber(){
        when(redisRepository.getProvider()).thenReturn(mapProviders);
        ProviderResponse providerResponse = messageService.processNumber("0034666111222", "Hello World!");

        assertThat(providerResponse.getProvider(), equalTo("P1"));
    }

    @Test
    public void shouldReturnAnExceptionWhenProcessNumber(){
        when(redisRepository.getProvider()).thenReturn(mapProviders);

        Exception exception = assertThrows(
                PrefixNotFoundException.class,
                () -> messageService.processNumber("0031666111222", "Hello World!"));

        assertEquals("El prefijo 0031 no es soportado por ningun provedor.", exception.getMessage());
    }




}