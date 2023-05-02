package app.bot.model.user;

import app.bot.model.message.MessageCore;

import java.util.List;

public interface UserCore {
	
	List<MessageCore> toSend();
	List<Integer> toDelete();
	Long getId();

}
