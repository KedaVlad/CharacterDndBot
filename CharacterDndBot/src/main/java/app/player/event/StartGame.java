package app.player.event;

import app.bot.model.user.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.telegram.telegrambots.meta.api.objects.Update;


@Getter
public class StartGame extends ApplicationEvent {

	private final User user;
	private final Update update;
	
	public StartGame(Object source, User user, Update update) {
		super(source);
		this.user = user;
		this.update = update;
	}

}
