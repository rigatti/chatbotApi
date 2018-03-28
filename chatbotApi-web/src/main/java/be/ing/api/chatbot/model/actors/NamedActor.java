package be.ing.api.chatbot.model.actors;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 *
 * Created by Freddy Snijder (ING) on 26/05/2017.
 *
 */

@Data
public class NamedActor extends Actor {
    @Setter(AccessLevel.NONE)
    private String name;

    public NamedActor(final ActorType actorType, final String name) {
        super(actorType);

        this.name = name;
    }

    @Override
    public boolean equals(final Actor actor) {
        boolean sameType = super.equals(actor);
        if (!sameType) {
            return false;
        }

        NamedActor bot        = (NamedActor)actor;
        String name         = bot.getName();

        boolean sameName = (name != null) && (name.equals(this.name));

        return sameName;
    }

}
