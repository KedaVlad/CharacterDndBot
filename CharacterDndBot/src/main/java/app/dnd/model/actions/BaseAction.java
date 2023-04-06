package app.dnd.model.actions;
 

import app.player.model.Stage;
import app.player.model.enums.Location;
import lombok.Data;

@Data
public abstract class BaseAction implements Stage {

	private Location location;
	private boolean mediator;
	private boolean replyButtons;
	private boolean cloud;
	private String name;
	private String text;

	public abstract String[][] buildButtons();

	public abstract BaseAction continueAction(String key);

	public abstract boolean hasButtons();

	public abstract boolean replyContain(String string);

}
