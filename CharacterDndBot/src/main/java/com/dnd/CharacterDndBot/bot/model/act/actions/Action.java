package com.dnd.CharacterDndBot.bot.model.act.actions;

import com.dnd.CharacterDndBot.dnd.dto.ObjectDnd;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Action extends BaseAction implements Cloneable {

	private static final long serialVersionUID = 1L;
	private String[][] buttons;
	private String[] answers;
	private ObjectDnd objectDnd;

	Action() {}

	public static ActionBuilder builder() {
		return new ActionBuilder();
	}

	@Override
	public String[][] buildButtons() {
		return buttons;
	}

	@Override
	public Action continueAction(String key) {
		Action answer = this.clone();
		if (answer.answers != null && answer.answers.length > 0) {
			String[] newAnswers = new String[answer.answers.length + 1];
			for (int i = 0; i < answer.answers.length; i++) {
				newAnswers[i] = answer.answers[i];
			}
			newAnswers[newAnswers.length - 1] = key;
			answer.answers = newAnswers;
		} else {
			answer.answers = new String[] { key };
		}
		return answer;
	}

	@Override
	public boolean hasButtons() {
		return buttons != null && buttons.length > 0 && buttons[0].length > 0;
	}

	@Override
	public boolean replyContain(String string) {
		if(hasButtons()) {
			for (String[] line : buttons) {
				for (String button : line) {
					if (button.equals(string)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	protected Action clone() {
		Action answer = new Action();
		answer.setName(getName());
		answer.objectDnd = this.objectDnd;
		answer.answers = this.answers;
		answer.setLocation(getLocation());
		answer.setKey(getKey());
		return answer;
	}

	public int condition()
	{
		int condition = 0;
		if (answers != null) condition = answers.length;
		return condition;
	}

}
