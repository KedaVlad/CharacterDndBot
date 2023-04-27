package app.player.event;

import app.bot.model.user.UserCore;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EndGame extends ApplicationEvent {

	private final UserCore user;
	public EndGame(Object source, UserCore user) {
		super(source);
		this.user = user;
	}
}
