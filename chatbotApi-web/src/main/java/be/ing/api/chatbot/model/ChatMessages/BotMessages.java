package be.ing.api.chatbot.model.ChatMessages;

import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Freddy Snijder (ING) on 19/06/2017.
 *
 */
public class BotMessages {

    static public abstract class AnyBotMessage extends ChatMessage {}

    @Data
    static public class Image extends AnyBotMessage {
        private URL url;
    }

    @Data
    static public class Card extends AnyBotMessage {
        private String title;
        private String subtitle;

        @Setter(AccessLevel.NONE)
        final private List<MessageButton> buttons = new ArrayList<>();

        public Card(String title, String subtitle) {
            this.title      = title;
            this.subtitle   = subtitle;
        }

        public void addButton(String text, String postback) {
            buttons.add(new MessageButton(text, postback));
        }
    }

    @Data
    static public class MultipleChoice extends AnyBotMessage {
        private String title;

        @Setter(AccessLevel.NONE)
        final private List<String> choices = new ArrayList<>();

        public MultipleChoice(String title) {
            this.title      = title;
        }

        public void addChoice(String choice) {
            choices.add(choice);
        }
    }

    @Data
    static public class Custom  extends AnyBotMessage {
        private JsonObject messageData;
    }
}
