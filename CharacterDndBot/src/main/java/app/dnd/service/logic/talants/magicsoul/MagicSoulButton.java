package app.dnd.service.logic.talants.magicsoul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.spells.MagicSoul;
import app.dnd.model.ability.spells.Spell;
import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;

public interface MagicSoulButton {

	public BaseAction[][] spell(Long id);
	public String[][] singleSpell(Spell spell);
}

@Component
class MagicSoulButtonBuilder implements MagicSoulButton {

	@Autowired
	private MagicSoulService magicSoulService;

	@Override
	public BaseAction[][] spell(Long id) {
		MagicSoul magicSoul = magicSoulService.getById(id);

		BaseAction[][] pool = new BaseAction[magicSoul.getPoolCantrips().getActive().size() + magicSoul.getPoolSpells().getActive().size()][1];
		int i = 0;
		for(Spell spell: magicSoul.getPoolCantrips().getActive()) {
			pool[i][0] = Action.builder().name(spell.getName()).objectDnd(spell).build();
			i++;
		}
		for(Spell spell: magicSoul.getPoolSpells().getActive()) {
			pool[i][0] = Action.builder().name(spell.getName()).objectDnd(spell).build();
			i++;
		}

		return pool;
	}

	@Override
	public String[][] singleSpell(Spell spell) {
		return new String[][] {{"Cast"}};
	}

}
