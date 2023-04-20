package app.user.model;

import java.util.ArrayList;
import java.util.List;
import app.bot.model.MessageCore;
import app.bot.model.UserCore;
import lombok.Data;

@Data
public class User implements UserCore {
	
	private Long id;
	private ActualHero actualHero;
	private Script script;
	private Trash trash;
	private MessageCore message;
	
	@Override
	public List<MessageCore> toSend() {
		List<MessageCore> pool = new ArrayList<>();
		if(actualHero.isReadyToGame()) pool.addAll(actualHero.getCloudsForSend());
		if(message != null) pool.add(message);
		return pool;
	}
	
	@Override
	public List<Integer> toDelete() {
		List<Integer> trash = new ArrayList<>();
		trash = this.trash.getCircle();
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
