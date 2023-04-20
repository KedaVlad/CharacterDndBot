package app.dnd.service.talants.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.telents.spells.Spell;
import app.dnd.service.talants.MagicSoulService;
import app.user.model.ActualHero;

public interface MagicSoulLogic {

	public List<Spell> spellList(ActualHero hero);
}

@Component
class MagicSoulFacade implements MagicSoulLogic {
	
	@Autowired
	private MagicSoulService magicSoulService;

	@Override
	public List<Spell> spellList(ActualHero hero) {
		
		List<Spell> target = new ArrayList<>();
		target.addAll(magicSoulService.findByIdAndOwnerName(hero.getId(), hero.getName()).getPoolCantrips().getActive());
		target.addAll(magicSoulService.findByIdAndOwnerName(hero.getId(), hero.getName()).getPoolSpells().getActive());
		return target;
	}
}