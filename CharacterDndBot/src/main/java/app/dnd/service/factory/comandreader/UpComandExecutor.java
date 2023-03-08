package app.dnd.service.factory.comandreader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.dto.CharacterDnd;
import app.dnd.dto.ObjectDnd;
import app.dnd.dto.characteristics.Hp;
import app.dnd.dto.characteristics.Skill;
import app.dnd.dto.characteristics.Stat;
import app.dnd.dto.comands.UpComand;
import app.dnd.service.logic.characteristic.SkillUp;
import app.dnd.service.logic.characteristic.StatUp;

@Component
class UpComandExecutor implements Uper<UpComand> {
	
	@Autowired
	private UpHpComand upHpComand;
	@Autowired
	private StatUpComand statUpComand;
	@Autowired
	private SkillUpComand skillUpComand;
	
	public void up(CharacterDnd character, UpComand comand) {

		ObjectDnd object = comand.getObject();
		if(object instanceof Hp) {
			upHpComand.up(character, (Hp)object);
		} else if(object instanceof Stat) {
			statUpComand.up(character, (Stat)object);
		} else if(object instanceof Skill) {
			skillUpComand.up(character, (Skill)object);
		}
	}
}

interface Uper<T extends ObjectDnd> {
	public abstract void up(CharacterDnd character, T target);
}

@Component
class UpHpComand implements Uper<Hp>{

	@Override
	public void up(CharacterDnd character, Hp target) {
		character.getHp().setHpBonus(character.getHp().getHpBonus() + target.getHpBonus());
	}
	
}

@Component
class StatUpComand implements Uper<Stat> {

	@Autowired
	private StatUp statUp;
	
	@Override
	public void up(CharacterDnd character, Stat target) {
		statUp.up(character, target.getName(), target.getValue());
	}
}

@Component
class SkillUpComand implements Uper<Skill> {

	@Autowired
	private SkillUp skillUp;
	
	@Override
	public void up(CharacterDnd character, Skill target) {
		skillUp.up(character, target);
	}
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	