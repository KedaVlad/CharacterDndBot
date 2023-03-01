package app.bot.model.wrapp;

import app.bot.model.user.User;
import lombok.Data;

@Data
public abstract class WrappForHandler<V> {
	
	private User user;
	private V target;
	
	public WrappForHandler(User user, V target) {
		this.user = user;
		this.target = target;
	}
}
