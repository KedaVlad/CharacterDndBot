package app.dnd.service.logic.characteristic.stat;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import app.dnd.model.characteristics.Characteristics;
import app.dnd.model.enums.Stats;
import app.dnd.service.logic.characteristic.CharacteristicsService;

@Component
public class StatSetup {
	
	@Autowired
	private CharacteristicsService characteristicsService;
	@Autowired
	private StatUper statUper;
	
	public void setup(Long id, int str, int dex, int con, int intl, int wis, int cha) {
		
		Characteristics characteristics = characteristicsService.getById(id);
		statUper.compleat(characteristics.getStats().get(Stats.STRENGTH), str);
		statUper.compleat(characteristics.getStats().get(Stats.DEXTERITY), dex);
		statUper.compleat(characteristics.getStats().get(Stats.CONSTITUTION), con);
		statUper.compleat(characteristics.getStats().get(Stats.INTELLIGENSE), intl);
		statUper.compleat(characteristics.getStats().get(Stats.WISDOM), wis);
		statUper.compleat(characteristics.getStats().get(Stats.CHARISMA), cha);
		characteristicsService.save(characteristics);
	}
}
