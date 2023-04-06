package app.dnd.service.logic.talants.magicsoul;

import org.springframework.stereotype.Component;

import app.dnd.model.ability.spells.Spell;

public interface MagicSoulInfo {

    public String spell();
    public String descriptionOfSpell(Spell spell);
}

@Component
class MagicSoulInformator implements MagicSoulInfo {

	@Override
	public String spell() {
		return "Here is your spells.";
	}

	@Override
	public String descriptionOfSpell(Spell spell) {
		return spell.getName() + "\n" + spell.getDescription();
	}
	
}