package be.ing.api.chatbot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.ipc.netty.http.client.HttpClient;

@Configuration
public class APIAIConfiguration {

    private final String API_PORT                       = "443";

    @Bean(name = "API_DOMAIN")
    public String API_DOMAIN() {
        return "api.api.ai";
    }

    @Bean(name = "API_PATH")
    public String API_PATH() {
        return "/v1/query";
    }

    @Bean(name = "API_VERSION")
    public String API_VERSION() {
        return "20150910";
    }

    @Bean(name = "API_TOKEN")
    public String API_TOKEN() { return "d882f9d3118d4f6986bcb77f1facab62"; }

    @Bean(name = "HttpClient")
    public HttpClient HttpClient() {
        return HttpClient.create();
    }
}
