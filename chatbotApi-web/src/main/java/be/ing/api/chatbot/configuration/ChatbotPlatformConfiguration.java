package be.ing.api.chatbot.configuration;

import be.ing.api.chatbot.service.APIAIBotProviderAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatbotPlatformConfiguration {
    @Bean
    public APIAIBotProviderAPI createBotProviderAPI() {
        return new APIAIBotProviderAPI();
    }
}
