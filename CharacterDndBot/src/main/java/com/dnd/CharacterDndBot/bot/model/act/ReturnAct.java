package com.dnd.CharacterDndBot.bot.model.act;

import com.dnd.CharacterDndBot.bot.model.act.actions.Action;

import lombok.Data;

@Data
public class ReturnAct implements Act {
	
	private static final long serialVersionUID = 1L;
	private String target;
	private String call;
	private ActiveAct act;
	private Action action;

	ReturnAct() {}

	public static ReturnActBuilder builder() {
		return new ReturnActBuilder();
	}
}
