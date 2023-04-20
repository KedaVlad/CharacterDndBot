package app.dnd.model.actions;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.enums.Proficiency;
import app.dnd.model.enums.Stats;
import app.dnd.util.math.Dice;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonTypeName("roll")
public class RollAction extends SingleAction {

	private Stats depends;
	private Proficiency proficiency;
	private Dice[] base;

	RollAction() {
	}

	public static RollActionBuilder buider() {
		return new RollActionBuilder();
	}

	public void addDicesToStart(Dice... dices) {
		if (base == null) {
			base = dices;
		} else {
			Dice[] result = new Dice[base.length + dices.length];
			for (int i = 0; i < dices.length; i++) {
				result[i] = dices[i];
			}
			int treker = 0;
			for (int i = dices.length; i < result.length; i++) {
				result[i] = base[treker];
				treker++;
			}
			base = result;
		}
	}

	public void addDicesToEnd(Dice... dices) {
		if (base == null) {
			base = dices;
		} else {
			Dice[] result = new Dice[base.length + dices.length];
			for (int i = 0; i < base.length; i++) {
				result[i] = base[i];
			}
			int treker = 0;
			for (int i = base.length; i < result.length; i++) {
				result[i] = dices[treker];
				treker++;
			}
			base = result;
		}
	}

	public Stats getDepends() {
		return depends;
	}

	public boolean isProficiency() {
		return proficiency != null;
	}

	public Proficiency getProficiency() {
		return proficiency;
	}

	public void setDepends(Stats depends) {
		this.depends = depends;
	}

	public void setProficiency(Proficiency proficiency) {
		this.proficiency = proficiency;
	}


	@Override
	public BaseAction continueStage(String key) {
		return this;
	}

	@Override
	public boolean hasButtons() {
		return false;
	}

	@Override
	public boolean containButton(String string) {
		return false;
	}

	@Override
	public String[][] buildButton() {
		return null;
	}
}
