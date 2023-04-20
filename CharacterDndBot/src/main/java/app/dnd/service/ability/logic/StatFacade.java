package app.dnd.service.ability.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.Ability;
import app.dnd.model.ability.Stat;
import app.dnd.model.enums.Stats;
import app.dnd.service.ability.AbilityService;
import app.dnd.util.math.Dice;
import app.dnd.util.math.Formalizer.Roll;
import app.user.model.ActualHero;

@Component
public class StatFacade implements StatLogic {
	
	@Autowired
	private AbilityService abilityService;
	
	@Override
	public Dice buildDice(ActualHero hero, Stats stat) {
		
		Ability characteristics = abilityService.findByIdAndOwnerName(hero.getId(), hero.getName());
		Stat targetStat = characteristics.getStats().get(stat);
		return new Dice(targetStat.getCore().toString(), characteristics.modificator(stat), Roll.NO_ROLL);
	}


	@Override
	public void setup(ActualHero actualHero, int str, int dex, int con, int intl, int wis, int cha) {
		Ability ability = abilityService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		ability.getStats().get(Stats.STRENGTH).up(str);
		ability.getStats().get(Stats.DEXTERITY).up(dex);
		ability.getStats().get(Stats.CONSTITUTION).up(con);
		ability.getStats().get(Stats.INTELLIGENSE).up(intl);
		ability.getStats().get(Stats.WISDOM).up(wis);
		ability.getStats().get(Stats.CHARISMA).up(cha);
		abilityService.save(ability);
	}


	@Override
	public void up(ActualHero actualHero, Stats stat, int value) {
		Ability ability = abilityService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		ability.getStats().get(stat).up(value);
		abilityService.save(ability);
	}
	

	@Override
	public boolean isReadyToGame(ActualHero actualHero) {
		return abilityService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName()).getStats().get(Stats.CONSTITUTION).getValue() != 0;
	}
}

