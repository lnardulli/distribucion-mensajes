package com.test.masvoz.rest;

import com.test.masvoz.model.response.ProviderResponse;
import com.test.masvoz.service.MessageDistribution;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class MessageControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected MessageDistribution messageDistribution;

    @Before
    public void setUp() {
        Mockito.reset(messageDistribution);
    }

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    public void testRestProcessMessage() throws Exception {

        // given
        UUID uuid = UUID.randomUUID();
        String commentBody = "{\"number\":\"0034666111222\", \"message\":\"Hello World!\"}";
        ProviderResponse response = ProviderResponse.builder().uuid(uuid).provider("P1").build();

        // when
        when(messageDistribution.processNumber("0034666111222", "Hello World!")).thenReturn(response);

        // then
        mockMvc.perform(post("/message/send")
                .content(commentBody)
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}