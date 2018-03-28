package be.ing.api.chatbot.model.actors;

/**
 *
 * Created by Freddy Snijder (ING) on 26/05/2017.
 *
 */

public class BotActor extends NamedActor {
    public BotActor(final String name) {
        super(ActorType.BOT_ACTOR, name);
    }
}
