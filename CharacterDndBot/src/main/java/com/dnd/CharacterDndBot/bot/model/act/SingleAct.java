package com.dnd.CharacterDndBot.bot.model.act;

import com.dnd.CharacterDndBot.bot.model.act.actions.BaseAction;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SingleAct extends ActiveAct {
 
	private static final long serialVersionUID = 1L;
	private BaseAction action;
	private String text;

	public static SingleActBuilder builder() {
		return new SingleActBuilder();
	}

	@Override
	public boolean hasAction() {
		return action != null;
	}

	@Override
	public boolean hasMediator() {
		return action != null && action.isMediator();
	}

	@Override
	public boolean hasReply(String string) {
		return action != null && action.replyContain(string);
	}

	@Override
	public boolean hasCloud() {
		return action != null && action.isCloud();
	}

	@Override
	public BaseAction getAction() {
		return action;
	}

}
