package com.dnd.CharacterDndBot.service.acts.actions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PreRoll extends BaseAction { 

	String[][] nextStep;
	RollAction action;
	private String status;
	private boolean criticalMiss;
	private boolean criticalHit;

	PreRoll() {
		nextStep = new String[][] { { "ADVENTURE", "BASIC", "DISADVENTURE" } };
	}

	public static PreRollBuilder builder() {
		return new PreRollBuilder();
	}

	@Override
	public PreRoll continueAction(String answer) {
		this.status = answer;
		return this;
	}

	@Override
	public String[][] buildButtons() {
		return nextStep;
	}

	@Override
	public boolean hasButtons() {
		return nextStep != null && nextStep.length != 0;
	}

	@Override
	public boolean replyContain(String string) {
		return string.equals("ADVENTURE") || string.equals("BASIC") || string.equals("DISADVENTURE");
	}

}
