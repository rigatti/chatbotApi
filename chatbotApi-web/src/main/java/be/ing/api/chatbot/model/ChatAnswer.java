package be.ing.api.chatbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ChatAnswer {
    @JsonProperty
    private int status;
    @JsonProperty
    private String message;
}
