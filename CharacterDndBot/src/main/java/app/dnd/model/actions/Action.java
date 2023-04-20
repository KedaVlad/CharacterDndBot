package app.dnd.model.actions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.ObjectDnd;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonTypeName("action")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME, property = "type")
public class Action extends SingleAction implements Cloneable {

	private String[] answers;
	private ObjectDnd objectDnd;

	Action() {}

	public static ActionBuilder builder() {
		return new ActionBuilder();
	}

	@Override
	public String[][] buildButton() {
		return getButtons();
	}

	@Override
	public Action continueStage(String key) {
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
		return getButtons() != null && getButtons().length > 0 && getButtons()[0].length > 0;
	}


	@Override
	protected Action clone() {
		Action answer = new Action();
		answer.objectDnd = this.objectDnd; 
		answer.setName(getName());
		answer.answers = this.answers;
		answer.setLocation(getLocation());
		return answer;
	}

	@Override
	public boolean containButton(String string) {
		if(hasButtons()) {
			for (String[] line : getButtons()) {
				for (String button : line) {
					if (button.equals(string)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public int condition() {
		int condition = 0;
		if (answers != null) condition = answers.length;
		return condition;
	}

}
