package app.bot.event;

import app.bot.model.UserCore;
import lombok.Data;

@Data
public class EndGame {

	private final UserCore user;
	public EndGame(UserCore user) {
		this.user = user;
	}
}
