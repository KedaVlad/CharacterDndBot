package app.bot.model.user;

import java.util.ArrayList;
import java.util.List;
import app.bot.model.message.MessageCore;
import lombok.Data;

@Data
public class User implements UserCore, UserCache {
	
	private Long id;
	private ActualHero actualHero;
	private Script script;
	private Trash trash;
	private MessageCore message;
	
	@Override
	public List<MessageCore> toSend() {
		List<MessageCore> pool = new ArrayList<>();
		pool.addAll(actualHero.getCloudsForSend());
		if(message != null) {
			pool.add(message);
			message = null;
		}
		return pool;
	}
	
	@Override
	public List<Integer> toDelete() {
		List<Integer> trash = new ArrayList<>(this.trash.getCircle());
		this.trash.getCircle().clear();
		return trash;
	}

	@Override
	public UserCore clear() {
		script.clear(trash);
		actualHero.clear(trash);
		message = null;
		return this;
	}
	
	public void reset() {
		actualHero.clear(trash);
	}
}
