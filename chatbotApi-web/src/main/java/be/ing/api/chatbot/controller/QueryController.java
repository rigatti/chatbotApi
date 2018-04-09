package be.ing.api.chatbot.controller;

import be.ing.api.chatbot.business.AiLogic;
import be.ing.api.chatbot.model.ChatAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private AiLogic aiLogic;

    @GetMapping(value = "", produces = "application/json")
    public Mono<ChatAnswer> get(@RequestParam String query) {
        return Mono.create(f -> {
                try {
                f.success(aiLogic.chat(query));
            } catch (Exception e) {
                ChatAnswer c = ChatAnswer.builder().status(500).message(e.getMessage()).build();
                f.success(c);
            }
        });
    }
}
