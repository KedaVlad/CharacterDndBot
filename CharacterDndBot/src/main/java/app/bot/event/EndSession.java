package app.bot.event;

import lombok.Data;

@Data
public class EndSession {

	private final Long id;
	
	public EndSession(Long id) {
		this.id = id;
	}
}
