package app.bot.model;

import java.util.List;

public interface UserCore {
	
	public List<MessageCore> toSend();
	public List<Integer> toDelete();
	public Long getId();
	public UserCore clear();
}
