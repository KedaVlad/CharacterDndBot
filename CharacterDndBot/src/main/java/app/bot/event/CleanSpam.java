package app.bot.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.telegram.telegrambots.meta.api.objects.Update;


@Getter
public class CleanSpam extends ApplicationEvent {

	private final Update update;
	
	public CleanSpam(Object source, Update update) {
		super(source);
		this.update = update;
	}
}
