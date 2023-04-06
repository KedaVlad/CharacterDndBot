package app.user.model;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.Update;

import app.bot.model.MessageCore;
import app.bot.model.UserCore;
import app.player.model.Stage;
import app.player.model.act.Act;
import lombok.Data;

@Data
public class User implements UserCore {
	
	private Long id;
	private ActualHero actualHero;
	private Script script;
	private Clouds clouds;
	private Trash trash;
	
	private Update update;
	private Stage stage;
	private Act act;
	private MessageCore message;
	
	@Override
	public List<MessageCore> toSend() {
		List<MessageCore> pool = new ArrayList<>();
		pool.addAll(actualHero.getClouds());
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
}
