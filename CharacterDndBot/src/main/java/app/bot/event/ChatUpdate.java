package app.bot.event;

import app.bot.model.user.UserCore;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ChatUpdate extends ApplicationEvent {

	private final UserCore user;

	public ChatUpdate(Object source, UserCore user) {
		super(source);
		this.user = user;
	}
}
