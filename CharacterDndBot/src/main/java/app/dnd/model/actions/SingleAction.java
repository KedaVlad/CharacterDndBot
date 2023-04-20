package app.dnd.model.actions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonTypeName("single_action")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Action.class, name = "action"),
        @JsonSubTypes.Type(value = PreRoll.class, name = "pre_roll"),
        @JsonSubTypes.Type(value = RollAction.class, name = "roll")
})
public abstract class SingleAction extends BaseAction {
	private String name;
	private String[][] buttons;
}
