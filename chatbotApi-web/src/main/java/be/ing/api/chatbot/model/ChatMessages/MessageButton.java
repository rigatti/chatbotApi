package be.ing.api.chatbot.model.ChatMessages;

/**
 *
 * Created by Freddy Snijder (ING) on 21/06/2017.
 *
 */
public class MessageButton {
    private String text;
    private String postback;

    public MessageButton(String text, String postback) {
        this.text = text;
        this.postback = postback;
    }
}
