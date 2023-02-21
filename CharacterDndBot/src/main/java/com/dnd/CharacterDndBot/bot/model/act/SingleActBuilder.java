package com.dnd.CharacterDndBot.bot.model.act;

import java.util.ArrayList;

import com.dnd.CharacterDndBot.bot.model.act.actions.BaseAction;

public class SingleActBuilder extends ActiveActBuilder<SingleActBuilder> {
	
	private BaseAction action;
	private String text;

	SingleActBuilder() {
	}

	public SingleActBuilder action(BaseAction action) {
		this.action = action;
		return this;
	}

	public SingleActBuilder text(String text) {
		this.text = text;
		return this;
	}

	public SingleAct build() {
		SingleAct answer = new SingleAct();
		answer.setName(this.name);
		answer.setText(this.text);
		answer.setAction(this.action);
		answer.setActCircle(new ArrayList<>());
		return answer;
	}

	@Override
	protected SingleActBuilder self() {
		return this;
	}
}