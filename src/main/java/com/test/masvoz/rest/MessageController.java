package com.test.masvoz.rest;

import com.test.masvoz.model.request.MessageRequest;
import com.test.masvoz.model.response.ProviderResponse;
import com.test.masvoz.service.MessageDistribution;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/message")
public class MessageController {

    MessageDistribution messageDistribution;

    public MessageController(MessageDistribution messageDistribution) {
        this.messageDistribution = messageDistribution;
    }

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public ProviderResponse sendMessage(@RequestBody MessageRequest messageRequest) {
        return messageDistribution.processNumber(messageRequest.getNumber(), messageRequest.getMessage());
    }

}
