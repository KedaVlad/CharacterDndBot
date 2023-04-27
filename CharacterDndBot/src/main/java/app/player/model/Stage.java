package app.player.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import app.dnd.model.actions.BaseAction;
import app.player.model.enums.Location;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BaseAction.class, name = "base_action"),
})
public interface Stage {
	
	 Stage continueStage(String key);
	 boolean hasButtons();
	 boolean containButton(String string);
	 Location getLocation();
	 String[][] buildButton();
	 String getText();
}
