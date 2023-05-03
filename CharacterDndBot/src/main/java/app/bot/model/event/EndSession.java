package app.bot.model.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EndSession extends ApplicationEvent {

	private final Long id;
	public EndSession(Object source, Long id) {
		super(source);
		this.id = id;
	}
}
