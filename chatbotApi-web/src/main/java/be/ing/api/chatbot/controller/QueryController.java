package be.ing.api.chatbot.controller;

import be.ing.api.chatbot.business.AiLogic;
import be.ing.api.chatbot.model.ChatAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private AiLogic aiLogic;

    @GetMapping(value = "", produces = "application/json")
    public Mono<ChatAnswer> get() {
        return Mono.create(f -> f.success(aiLogic.chat()));
    }
}
