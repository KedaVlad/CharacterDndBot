package app.dnd.service.logic.characteristic.stat;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.dnd.model.characteristics.Characteristics;
import app.dnd.model.characteristics.Stat;
import app.dnd.model.enums.Stats;
import app.dnd.service.logic.characteristic.CharacteristicsService;
import app.dnd.util.ArrayToColumns;
import app.player.model.enums.Location;

public interface StatButton {

	public BaseAction[][] menu(Long id);
	public String[][] targetChangeButtons(Stat stat);
	
}

@Component
class StatButtonsBuilder implements StatButton{

	@Autowired
	private ArrayToColumns arrayToColumns;
	@Autowired
	private CharacteristicsService characteristicsService;

	@Override
	public BaseAction[][] menu(Long id) {

		Characteristics characteristics = characteristicsService.getById(id);
		Map<Stats, Stat> stats = characteristics.getStats();
		BaseAction[] arr = new BaseAction[stats.size()];
		
		int i = 0;
		for(Stats stat: stats.keySet()) {
			arr[i] = Action.builder()
					.name(stats.get(stat).getValue() + "["+ characteristics.modificator(stat) + "] " + stats.get(stat).getName())
					.objectDnd(stats.get(stat))
					.location(Location.CHARACTERISTIC)
					.build();
			i++;
		}
		return arrayToColumns.rebuild(arr, 2, BaseAction.class);
	}

	@Override
	public String[][] targetChangeButtons(Stat stat) {
		String[][] base = new String[][] {{"-3","-2","-1","+1","+2","+3"}};
		int max = stat.getMaxValue() - stat.getValue();
		int min = stat.getValue() - 3;
		if(max > 3) max = 3;
		if(min > 3) min = 3;
		String[][] answer = new String[1][min+max];
		if(answer[0].length == 6) {
			return base;
		} else if(min < max) {
			int j = 0;
			for(int i = 6 - answer[0].length; i < 6; i++) {
				answer[0][j] = base[0][i];
				j++;
			}
		} else {
			for(int i = 0; i < answer[0].length; i ++) {
				answer[0][i] = base[0][i];
			}
		}
		return answer;
	}
	
}
