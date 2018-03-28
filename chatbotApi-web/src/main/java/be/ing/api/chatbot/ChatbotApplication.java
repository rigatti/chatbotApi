package be.ing.api.chatbot;

import be.ing.api.chatbot.configuration.APIAIConfiguration;
import be.ing.api.chatbot.configuration.ChatbotPlatformConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ChatbotPlatformConfiguration.class, APIAIConfiguration.class})
public class ChatbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatbotApplication.class, args);
	}
}
