package be.ing.api.chatbot.model.actors;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 *
 * Actor
 *
 */
@Data
public abstract class Actor {
    @Setter(AccessLevel.NONE)
    private ActorType actorType = ActorType.UNKNOWN_ACTOR;

    protected Actor(final ActorType actorType) {
        this.actorType = actorType;
    }

    public boolean equals(final Actor actor) {
        return (actor != null) && (actor.getActorType() == actorType);
    }
}

