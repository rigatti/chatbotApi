package be.ing.api.chatbot.service;

import be.ing.api.chatbot.helpers.JSONUtils;
import be.ing.api.chatbot.model.ChatMessages.BotMessages;
import be.ing.api.chatbot.model.ChatMessages.ChatMessage;
import be.ing.api.chatbot.model.ChatMessages.TextMessage;
import be.ing.api.chatbot.model.actors.BotActor;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.client.HttpClient;
import reactor.ipc.netty.http.client.HttpClientResponse;
import reactor.ipc.netty.options.ClientProxyOptions;

import java.net.URL;
import java.util.*;

import static reactor.ipc.netty.options.ClientProxyOptions.*;

public class APIAIBotProviderAPI {

    @Qualifier("API_VERSION")
    @Autowired
    private String API_VERSION;

    @Qualifier("API_DOMAIN")
    @Autowired
    private String API_DOMAIN;

    @Qualifier("API_PATH")
    @Autowired
    private String API_PATH;

    @Qualifier("API_TOKEN")
    @Autowired
    private String API_TOKEN;

    @Autowired
    HttpClient httpClient;

    private final int RESPONSE_TIMEOUT                  = 10; // Seconds

    private final int MESSAGE_TEXT_TYPE                 = 0;
    private final int MESSAGE_IMAGE_TYPE                = 3;
    private final int MESSAGE_CARD_TYPE                 = 1;
    private final int MESSAGE_MULTIPLE_CHOICE           = 2;
    private final int MESSAGE_CUSTOM_TYPE               = 4;

    private final String SERVICE_NAME                   = "API.ai";
    private final BotActor API_AI_CHATBOT_ACTOR         = new BotActor(SERVICE_NAME);

    public String getResponse() {

        ClientHttpConnector httpConnector = new ReactorClientHttpConnector();
        WebClient webClient = WebClient.builder()
                                .baseUrl("https://api.dialogflow.com")
                                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                                .defaultHeader(HttpHeaders.CONTENT_LENGTH, "64")
                                //.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + API_TOKEN)
                                .build();

        String body = buildRequestBody().toString();
        WebClient.RequestHeadersSpec<?> requestSpec = webClient
                                                .method(HttpMethod.POST)
                                                .uri("/v1/query?v=" + API_VERSION)
                                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                                .contentLength(body.length())
                                                .accept(MediaType.ALL)
                                                .body(BodyInserters.fromObject(body));

        String response = requestSpec.exchange()
                .block()
                .bodyToMono(String.class)
                .block();

//        HttpClient client = HttpClient.create(o -> o.proxy(ops -> ops.type(Proxy.HTTP)
//                .host("127.0.0.1")
//                .port(8888)
//                .nonProxyHosts("spring.io")));

        return response;
    }

    private JsonObject buildRequestBody() {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("lang", "fr");
        jsonObject.addProperty("sessionId", __createSessionID() );
        jsonObject.addProperty("query", "List of competitions" );

        return jsonObject;
    }

    protected String __createSessionID() {
        UUID uniqueKey = UUID.randomUUID();
        return uniqueKey.toString();
    }

    protected List<ChatMessage> __createBotMessages(JsonArray messages) {

        List<ChatMessage> botMessages = null;

        if (messages==null) {
            //LOG.warn("No messages available in the bot response, unable to create not messages.");
            return botMessages;
        }

        try {
            int numMessages = messages.size();
            if (numMessages == 0) {
                //LOG.warn("Empty message list in bot response");
            }

            botMessages = new ArrayList<>(numMessages);

            for (JsonElement message : messages) {
                JsonObject msgObj = message.getAsJsonObject();

                if(!__isMessageBelongsToCurrentPlatform(msgObj.get("platform"))) {
                    continue;
                }

                int type = msgObj.get("type").getAsInt();

                ChatMessage botMessage = null;
                switch(type) {
                    case MESSAGE_TEXT_TYPE:
                        botMessage = __createTextMessageFrom(msgObj);
                        break;
                    case MESSAGE_IMAGE_TYPE:
                        botMessage = __createImageMessageFrom(msgObj);
                        break;
                    case MESSAGE_CARD_TYPE:
                        botMessage = __createCardMessageFrom(msgObj);
                        break;
                    case MESSAGE_MULTIPLE_CHOICE:
                        botMessage = __createMultipleChoiceMessageFrom(msgObj);
                        break;
                    case MESSAGE_CUSTOM_TYPE:
                        botMessage = __createCustomMessageFrom(msgObj);
                        break;
                    default:
                        //LOG.error("Unknown message type {}, unable to create bot message", type);
                }

                if (botMessage != null) {
                    botMessages.add(botMessage);
                } else {
                    //LOG.error("Unable to create message of type {}, skipping ...", type);
                }
            }
        } catch (Exception e) {
            //LOG.error("An exception occurred, unable to create bot message(s)", e);
        }

        return botMessages;
    }

    protected boolean __isMessageBelongsToCurrentPlatform(JsonElement platformEl){
        String platform = platformEl != null ? platformEl.getAsString() : null;

        return false;
    }

    protected TextMessage __createTextMessageFrom(JsonObject message) {
        // Speech is mandatory, so let it fail if not found
        String speech = message.get("speech").getAsString();
        if(!speech.isEmpty() || speech.equals("")) {
            return new TextMessage(speech);
        }

        return null;
    }

    protected BotMessages.Image __createImageMessageFrom(JsonObject message) {
        BotMessages.Image m = null;

        try {
            m = new BotMessages.Image();
            String urlStr = message.get("imageUrl").getAsString();
            URL url = new URL(urlStr);

            m.setUrl(url);
        } catch (Exception e) {
            //LOG.error("An exception occurred, unable to create image message", e);
        }

        return m;
    }

    protected BotMessages.Card __createCardMessageFrom(JsonObject message) {
        JsonElement title = message.get("title");
        JsonElement subtitle = message.get("subtitle");

        BotMessages.Card m = new BotMessages.Card(
                title.getAsString(), // is mandatory, so throws exception on failure
                (subtitle != null) ? subtitle.getAsString() : null); // subtitle not mandatory

        // Buttons are mandatory so let if fail when not found
        JsonArray buttons = message.get("buttons").getAsJsonArray();
        for (JsonElement button : buttons) {
            JsonObject b = button.getAsJsonObject(); // if not object throw exception

            // Mandatory, so let it fail if not found
            String text = b.get("text").getAsString();

            // Not mandatory
            JsonElement postbackOptional = b.get("postback");
            String postback = (postbackOptional != null) ? postbackOptional.getAsString() : null;

            m.addButton(text, postback);
        }

        return m;
    }

    protected BotMessages.MultipleChoice __createMultipleChoiceMessageFrom(JsonObject message) {
        // Title is mandatory, so let it fail if not found
        BotMessages.MultipleChoice m = new BotMessages.MultipleChoice(message.get("title").getAsString());

        // Choices are mandatory so let if fail when not found
        JsonArray choices = message.get("replies").getAsJsonArray();
        for (JsonElement choice : choices) {
            m.addChoice(choice.getAsString());
        }

        return m;
    }

    protected BotMessages.Custom __createCustomMessageFrom(JsonObject message) {
        BotMessages.Custom m = new BotMessages.Custom();

        // payload is mandatory, so let it fail if not found
        m.setMessageData(message.get("payload").getAsJsonObject());

        return m;
    }
}
