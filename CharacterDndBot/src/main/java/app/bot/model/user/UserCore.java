package app.bot.model.user;

import app.bot.model.message.MessageCore;

import java.util.List;

public interface UserCore {
	
	public List<MessageCore> toSend();
	public List<Integer> toDelete();
	public Long getId();

}
