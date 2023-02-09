package com.dnd.CharacterDndBot.service.acts.actions;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency.Proficiencies.Proficiency;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Stat.Stats;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Dice;

public class RollActionBuilder extends BaseActionBuilder<RollActionBuilder> {
	
	private Stats depends;
	private Proficiency proficiency;
	private Dice[] base;

	RollActionBuilder() {
	}

	public RollActionBuilder statDepend(Stats stat) {
		this.depends = stat;
		return this;
	}

	public RollActionBuilder proficiency(Proficiency proficiency) {
		this.proficiency = proficiency;
		return this;
	}

	public RollActionBuilder diceCombo(Dice... dices) {
		this.base = dices;
		return this;
	}

	public RollAction build() {
		RollAction answer = new RollAction();
		answer.setName(name);
		answer.setLocation(location);
		answer.setDepends(depends);
		answer.setProficiency(proficiency);
		answer.setBase(this.base);
		return answer;
	}

	@Override
	protected RollActionBuilder self() {
		return this;
	}
}
