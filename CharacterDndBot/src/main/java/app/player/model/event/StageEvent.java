package app.player.model.event;

import app.player.model.Stage;
import app.bot.model.user.User;

public class StageEvent extends UserEvent<Stage> {
    public StageEvent(Object source, User user, Stage tusk) {
        super(source, user, tusk);
    }
}
