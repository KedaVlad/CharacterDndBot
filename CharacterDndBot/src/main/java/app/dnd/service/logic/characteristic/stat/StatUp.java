package app.dnd.service.logic.characteristic.stat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.characteristics.Characteristics;
import app.dnd.model.characteristics.Stat;
import app.dnd.model.enums.Stats;
import app.dnd.service.logic.characteristic.CharacteristicsService;

@Component
public class StatUp {

	@Autowired
	private CharacteristicsService characteristicsService;
	@Autowired
	private StatUper statUper;

	public void up(Long id, Stats stat, int value) {

		Characteristics characteristics = characteristicsService.getById(id);
		statUper.compleat(characteristics.getStats().get(stat), value);
		characteristicsService.save(characteristics);
	}


	
}

@Component
class StatUper {
	
	void compleat(Stat stat, int value) {

		stat.setValue(stat.getValue() + value);
		if (stat.getValue() > stat.getMaxValue()) {
			stat.setValue(stat.getMaxValue());
		} else if(stat.getValue() < stat.getMinValue()) {
			stat.setValue(stat.getMinValue());
		}
	}
}
