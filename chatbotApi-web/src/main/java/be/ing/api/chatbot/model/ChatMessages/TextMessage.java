package be.ing.api.chatbot.model.ChatMessages;

import lombok.Data;

/**
 *
 * Created by Freddy Snijder (ING) on 03/07/2017.
 *
 */
@Data
public class TextMessage extends ChatMessage {
    private String message;

    public TextMessage(String message) {
        this.message = message;
    }

    // Dummy constructor for deserialization, do not explicitly use
    public TextMessage() {}
}
