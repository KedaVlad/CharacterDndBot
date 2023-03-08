package app.dnd.service.logic.characteristic;

import org.springframework.stereotype.Component;

import app.dnd.dto.CharacterDnd;
import app.dnd.dto.characteristics.Stat;
import app.dnd.dto.characteristics.Stat.Stats;

@Component
public class StatUp {

	public void up(CharacterDnd character, Stats stat, int value) {
		
		for(Stat stats: character.getCharacteristics().getStats()) {
			if(stats.getName().equals(stat)) {
				up(stats, value);
				return;
			}
		}
	}

	private void up(Stat stat, int value) {

		stat.setValue(stat.getValue() + value);
		if (stat.getValue() > stat.getMaxValue()) {
			stat.setValue(stat.getMaxValue());
		} else if(stat.getValue() < stat.getMinValue()) {
			stat.setValue(stat.getMinValue());
		}
	}
}
