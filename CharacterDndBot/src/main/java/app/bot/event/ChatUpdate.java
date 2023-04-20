package app.bot.event;

import app.bot.model.UserCore;
import lombok.Data;

@Data
public class ChatUpdate {

	private final UserCore user;
	
	public ChatUpdate(UserCore user) {
		this.user = user;
	}
}
