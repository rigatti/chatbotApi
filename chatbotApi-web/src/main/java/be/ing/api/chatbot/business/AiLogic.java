package be.ing.api.chatbot.business;

import be.ing.api.chatbot.model.ChatAnswer;
import be.ing.api.chatbot.service.APIAIBotProviderAPI;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.client.HttpClientResponse;

@Component
public class AiLogic {

    @Getter
    @Autowired
    private APIAIBotProviderAPI providerAPI;

    public ChatAnswer chat() {

        //Request request = this.providerAPI.buildRequest(context);
        //Response response = this.providerAPI.getResponse(request);
        //return this.providerAPI.updateContext(context, response);
        final String response = providerAPI.getResponse();

        return ChatAnswer.builder().status(200).message(response).build();
    }
}
