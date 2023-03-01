package app.dnd.dto.stuffs.items;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.dto.ObjectDnd;
import lombok.Data;
import lombok.EqualsAndHashCode;
@JsonTypeName("items")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME, property = "_class")
@JsonSubTypes({ 
	@JsonSubTypes.Type(value = Items.class, name = "items"),
	@JsonSubTypes.Type(value = Armor.class, name = "armor"),
		@JsonSubTypes.Type(value = Pack.class, name = "pack"),
		@JsonSubTypes.Type(value = Ammunition.class, name = "ammunition"),
		@JsonSubTypes.Type(value = Tool.class, name = "tool"),
		@JsonSubTypes.Type(value = Weapon.class, name = "weapon") })

@Data
@EqualsAndHashCode(callSuper=false)
public class Items extends ObjectDnd {
	
	private String name;
	private String description;
	private boolean used;
}
