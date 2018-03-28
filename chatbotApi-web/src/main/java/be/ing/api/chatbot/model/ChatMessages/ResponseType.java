package be.ing.api.chatbot.model.ChatMessages;

public enum ResponseType {
    UNKNOWN(-1),
    DIRECT_ANSWERS(1),
    CHAT(2);

    private int value;

    ResponseType(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
