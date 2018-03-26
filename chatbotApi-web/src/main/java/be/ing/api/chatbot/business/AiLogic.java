package be.ing.api.chatbot.business;

import be.ing.api.chatbot.model.ChatAnswer;
import org.springframework.stereotype.Component;

@Component
public class AiLogic {
    public ChatAnswer chat() {
        return ChatAnswer.builder().status(200).message("Reply from Bot").build();
    }
}
