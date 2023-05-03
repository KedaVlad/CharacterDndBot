package app.player.model.event;

import app.bot.model.user.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UpdateEvent extends UserEvent<Update> {

    public UpdateEvent(Object source, User user, Update tusk) {
        super(source, user, tusk);
    }
}
