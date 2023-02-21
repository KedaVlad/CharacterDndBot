package com.dnd.CharacterDndBot.dnd.util.pools;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME, property = "POOL")
@JsonSubTypes({ @JsonSubTypes.Type(value = SimplePool.class, name = "SIMPLE_POOL") })
public abstract class Pool<T> {

	protected List<T> active;

	{
		active = new ArrayList<>();
	}
	
	public List<T> getActive() {
		return active;
	}

	public void setActive(List<T> active) {
		this.active = active;
	}
}
