package app.dnd.service.hp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.dnd.model.ability.Ability;
import app.dnd.model.ability.Hp;
import app.dnd.model.enums.Stats;
import app.dnd.model.hero.ClassDnd;
import app.dnd.model.hero.ClassesDnd;
import app.dnd.service.ability.AbilityService;
import app.dnd.service.classes.ClassesDndService;
import app.dnd.util.Convertor;
import app.dnd.util.math.Formalizer;
import app.bot.model.user.ActualHero;

@Service
public class HpFacade implements HpLogic {

	@Autowired
	private HpBuilder hpBuilder;
	@Autowired
	private HpService hpService;
	
	public void grow(ActualHero actualHero, int value) {
		Hp hp = hpService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		hp.grow(value);
		hpService.save(hp);
	}

	@Override
	public void damage(ActualHero actualHero, int value) {
		Hp hp = hpService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		hp.damaged(value);
		hpService.save(hp);
	}

	@Override
	public void heal(ActualHero actualHero, int value) {
		Hp hp = hpService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		hp.heal(value);
		hpService.save(hp);
	}

	@Override
	public void bonusHp(ActualHero actualHero, int value) {
		Hp hp = hpService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		hp.addTimeHp(value);
		hpService.save(hp);
	}
	
	@Override
	public void setUpRandom(ActualHero actualHero) {
		Hp hp = hpService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		hp.grow(hpBuilder.buildRandomBase(hp));
		hpService.save(hp);
	}
	
	@Override
	public void setUpStable(ActualHero actualHero) {
		Hp hp = hpService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		hp.grow(hpBuilder.buildStableBase(hp));
		hpService.save(hp);
	}

	@Override
	public int buildValueStableHp(ActualHero actualHero) {
		return hpBuilder.buildStableBase(hpService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName()));
	}

	@Override
	public int buildValueRandomHp(ActualHero actualHero) {
		return hpBuilder.buildRandomBase(hpService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName()));
	}
	

}


@Component
class HpBuilder {

	private Convertor hp = x -> x / 2 + 1;
	@Autowired
	private AbilityService abilityService;
	@Autowired
	private ClassesDndService classesDndService;
	
	public int buildStableBase(Hp hp) {
		int modificator = abilityService.findByIdAndOwnerName(hp.getUserId(), hp.getOwnerName()).modification(Stats.CONSTITUTION) + hp.getHpBonus();
		ClassesDnd classes = classesDndService.findByIdAndOwnerName(hp.getUserId(), hp.getOwnerName());
		int classHp = classes.getDndClass().get(0).getFirstHp();
		int hpValue = this.hp.convert(classHp);
		int start =  classHp + modificator;

		for (int i = 1; i < classes.getDndClass().get(0).getLvl(); i++) {
			if((hpValue + modificator) > 0) {
				start += hpValue + modificator;
			} else {
				start += 1;
			}			
		}
		return start;
	}
	
	public int buildRandomBase(Hp hp) {

		int modificator = abilityService.findByIdAndOwnerName(hp.getUserId(), hp.getOwnerName()).modification(Stats.CONSTITUTION) + hp.getHpBonus();
		ClassesDnd classes = classesDndService.findByIdAndOwnerName(hp.getUserId(), hp.getOwnerName());
		int hpValue = 0;
		int start = classes.getDndClass().get(0).getFirstHp() + modificator;
		for (int i = 1; i < classes.getDndClass().get(0).getLvl(); i++) {
			hpValue += Formalizer.roll(classes.getDndClass().get(0).getDiceHp()) + modificator;
			if(hpValue > 0) {
				start += hpValue;
			} else {
				start += 1;
			}
		}
		return start; 
	}
	
	public int buildStableForLvlUp(Ability ability, ClassDnd clazz,  Hp hp) {
		int modificator = ability.modification(Stats.CONSTITUTION) + hp.getHpBonus();
		int hpValue = this.hp.convert(clazz.getFirstHp());
		if((hpValue + modificator) > 0) {
			return hpValue + modificator;
		} else {
			return 1;
		}
	}
	
	public int buildRandomForLvlUp(Ability ability, ClassDnd clazz,  Hp hp) {
		int modificator = ability.modification(Stats.CONSTITUTION) + hp.getHpBonus();
		int hpValue = Formalizer.roll(clazz.getDiceHp());
		if((hpValue + modificator) > 0) {
			return hpValue + modificator;
		} else {
			return 1;
		}
	}
}
