package app.player.event;

import app.bot.model.user.User;
import app.player.model.act.Act;

public class ActEvent extends UserEvent<Act> {
    public ActEvent(Object source, User user, Act tusk) {
        super(source, user, tusk);
    }
}
