package app.bot.model.act.actions;
 

import app.dnd.service.Location;
import lombok.Data;

@Data
public abstract class BaseAction{

	private Location location;
	private boolean mediator;
	private boolean replyButtons;
	private boolean cloud;
	private long key;
	private String name;

	public abstract String[][] buildButtons();

	public abstract BaseAction continueAction(String key);

	public abstract boolean hasButtons();

	public abstract boolean replyContain(String string);

}
