package app.dnd.model.actions;
 

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import app.player.model.Stage;
import app.player.model.enums.Location;
import lombok.Data;

@Data
@JsonTypeName("base_action")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SingleAction.class, name = "single_action"),
        @JsonSubTypes.Type(value = PoolActions.class, name = "pool_action"),
})
public abstract class BaseAction implements Stage {

	private Location location;
	private String text;
}

