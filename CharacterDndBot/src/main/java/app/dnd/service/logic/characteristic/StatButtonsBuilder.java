package app.dnd.service.logic.characteristic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.bot.model.act.actions.Action;
import app.bot.model.act.actions.BaseAction;
import app.bot.service.ActualHeroService;
import app.dnd.dto.characteristics.Stat;
import app.dnd.service.Location;
import app.dnd.util.ArrayToColumns;

@Component
public class StatButtonsBuilder {

	@Autowired
	private StatModificator statModificator;
	@Autowired
	private ActualHeroService actualHeroService;
	@Autowired
	private ArrayToColumns arrayToColumns;
	

	public BaseAction[][] build(Long id) {

		Stat[] stats = actualHeroService.getById(id).getCharacter().getCharacteristics().getStats();
		BaseAction[] arr = new BaseAction[stats.length];
		for(int i = 0; i < stats.length; i++) {
			arr[i] = Action.builder()
					.name(stats[i].getValue() + "["+ statModificator.modificate(stats[i]) + "] " + stats[i].getName()).objectDnd(stats[i])
					.location(Location.CHARACTERISTIC)
					.build();
		}
		return arrayToColumns.rebuild(arr, 2, BaseAction.class);
	}

}
