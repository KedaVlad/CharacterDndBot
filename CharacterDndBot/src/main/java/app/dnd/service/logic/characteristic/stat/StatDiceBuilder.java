package app.dnd.service.logic.characteristic.stat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.characteristics.Characteristics;
import app.dnd.model.characteristics.Stat;
import app.dnd.model.enums.Stats;
import app.dnd.service.logic.characteristic.CharacteristicsService;
import app.dnd.util.math.Dice;
import app.dnd.util.math.Formalizer.Roll;

@Component
public class StatDiceBuilder {
	
	@Autowired
	private CharacteristicsService characteristicsService;

	public Dice build(Long id, Stats stat) {
		Characteristics characteristics = characteristicsService.getById(id);
		Stat targetStat = characteristics.getStats().get(stat);
		return new Dice(targetStat.getName().toString(), characteristics.modificator(stat),Roll.NO_ROLL);
	}

}
