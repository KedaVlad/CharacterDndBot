package com.dnd.CharacterDndBot.dnd.util.pools;

import java.util.ArrayList;
import java.util.List;

public class SecondTypePool<T> extends SimplePool<T> {
 
	private List<T> weating = new ArrayList<>();

	public void pull(int target) {
		if (this.getActive().size() >= getActiveMaxSize()) {
			return;
		}
		getActive().add(getWeating().get(target));
		getWeating().remove(target);
	}

	public void giveBack(int target) {
		getWeating().add(getActive().get(target));
		getActive().remove(target);
	}

	public List<T> getWeating() {
		return weating;
	}

	public void setWeating(List<T> weating) {
		this.weating = weating;
	}

	public void add(T object) {
		weating.add(object);
	}
}
