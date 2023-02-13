package com.dnd.CharacterDndBot.service.acts.actions;
 
import java.io.Serializable;

import com.dnd.CharacterDndBot.service.dndTable.dndService.Location;
import lombok.Data;

@Data
public abstract class BaseAction implements Serializable{

	private static final long serialVersionUID = 1L;
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