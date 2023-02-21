package com.dnd.CharacterDndBot.bot.model.act.actions;

import com.dnd.CharacterDndBot.dnd.service.Location;

public abstract class BaseActionBuilder<T extends BaseActionBuilder<T>> {
	
	protected Location location;
	protected String name;
	protected boolean mediator;
	protected boolean replyButtons;
	protected boolean cloud;

	protected abstract T self();

	public T mediator() {
		this.mediator = true;
		this.cloud = false;
		return self();
	}

	public T cloud() {
		this.cloud = true;
		this.mediator = false;
		this.replyButtons = false;
		return self();
	}

	public T replyButtons() {
		this.replyButtons = true;
		this.cloud = false;
		return self();
	}

	public T location(Location location) {
		this.location = location;
		return self();
	}

	public T name(String name) {
		this.name = name;
		return self();
	}

}
