package app.player.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import app.dnd.model.actions.BaseAction;
import app.player.model.enums.Location;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BaseAction.class, name = "base_action"),
})
public interface Stage {
	
	public abstract Stage continueStage(String key);
	public boolean hasButtons();
	public boolean containButton(String string);
	public Location getLocation();
	public String[][] buildButton();
	public String getText();
}
