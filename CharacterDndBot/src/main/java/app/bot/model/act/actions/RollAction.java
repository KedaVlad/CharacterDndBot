package app.bot.model.act.actions;

import app.dnd.dto.ability.proficiency.Proficiencies.Proficiency;
import app.dnd.dto.characteristics.Stat.Stats;
import app.dnd.util.math.Dice;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RollAction extends BaseAction {

	private static final long serialVersionUID = 1L;
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
	public String[][] buildButtons() {
		return null;
	}

	@Override
	public BaseAction continueAction(String key) {
		return this;
	}

	@Override
	public boolean hasButtons() {
		return false;
	}

	@Override
	public boolean replyContain(String string) {
		return false;
	}
}
