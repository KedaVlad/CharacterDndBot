package app.player.model.act;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
@JsonTypeName("tree_act")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SingleAct.class, name = "single_act"),
        @JsonSubTypes.Type(value = ArrayActs.class, name = "array_acts")
})
public abstract class TreeAct extends ActiveAct {

	private boolean reply;
	private boolean mediator;
	public abstract boolean replyContain(String target);
	
	public TreeAct() {}
		
}
