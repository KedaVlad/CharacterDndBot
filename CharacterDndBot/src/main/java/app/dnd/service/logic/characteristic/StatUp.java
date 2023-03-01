package app.dnd.service.logic.characteristic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.bot.model.ActualHero;
import app.bot.service.ActualHeroService;
import app.dnd.dto.characteristics.Stat;
import app.dnd.dto.characteristics.Stat.Stats;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StatUp {

	@Autowired
	private ActualHeroService actualHeroService;

	public void up(Long id, Stats stat, int value) {
		ActualHero actualHero = actualHeroService.getById(id);
		for(Stat stats: actualHero.getCharacter().getCharacteristics().getStats()) {
			if(stats.getName().equals(stat)) {
				up(stats, value);
				actualHeroService.save(actualHero);
				return;
			}
		}
		log.error("StatUp : ActualHero character dosen`t has stat - " + stat);
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
