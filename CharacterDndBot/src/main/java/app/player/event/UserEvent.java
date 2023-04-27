package app.player.event;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import app.bot.model.user.User;

@Getter
public class UserEvent<T> extends ApplicationEvent {

	private final User user;
	private final T tusk;

	public UserEvent(Object source, User user, T tusk) {
		super(source);
		this.user = user;
		this.tusk = tusk;
	}

}
