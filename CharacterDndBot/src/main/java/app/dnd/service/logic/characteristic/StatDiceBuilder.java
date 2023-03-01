package app.dnd.service.logic.characteristic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.bot.service.ActualHeroService;
import app.dnd.dto.CharacterDnd;
import app.dnd.dto.characteristics.Stat;
import app.dnd.dto.characteristics.Stat.Stats;
import app.dnd.util.math.Dice;
import app.dnd.util.math.Formalizer.Roll;

@Component
public class StatDiceBuilder {
	
	@Autowired
	private StatModificator statModificator;
	@Autowired
	private ActualHeroService actualHeroService;
	
	public Dice build(Long id, Stats stat) {
		CharacterDnd character = actualHeroService.getById(id).getCharacter();
		for(Stat stata: character.getCharacteristics().getStats()) {
			if(stata.getName() == stat) {
				return new Dice(stata.getName().toString(),statModificator.modificate(stata),Roll.NO_ROLL);
			}
		}
		return null;
	}

}
