package be.ing.api.chatbot.model.ChatMessages;

import lombok.Data;

/**
 *
 * Created by Freddy Snijder (ING) on 19/06/2017.
 *
 */
public class UserMessages {

    static public abstract class AnyUserMessage extends ChatMessage {}

    @Data
    static public class CardResponse extends AnyUserMessage {
        private MessageButton selectedButton;

        public CardResponse(String selectedButtonText, String selectedButtonPostback) {
            this.selectedButton = new MessageButton(selectedButtonText, selectedButtonPostback);
        }
    }

    @Data
    static public class MultipleChoiceResponse extends AnyUserMessage {
        private String choice;

        public MultipleChoiceResponse(String choice) {
            this.choice = choice;
        }
    }

    static public class ResetContext extends AnyUserMessage {}

}
