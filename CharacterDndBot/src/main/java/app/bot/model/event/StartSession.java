package app.bot.model.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public class StartSession extends ApplicationEvent {
    private final Update update;
    public StartSession(Object source, Update update) {
        super(source);
        this.update = update;
    }
}
