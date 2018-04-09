package be.ing.api.chatbot.business;

import be.ing.api.chatbot.model.ChatAnswer;
import be.ing.api.chatbot.model.ChatMessages.ChatMessage;
import be.ing.api.chatbot.service.APIAIBotProviderAPI;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AiLogic {

    @Getter
    @Autowired
    private APIAIBotProviderAPI providerAPI;

    public ChatAnswer chat(String query) throws Exception {

        final List<ChatMessage> response = providerAPI.getResponse(query);

        return ChatAnswer.builder().status(200).message(response.toString()).build();
    }
}
