package com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency.Possession;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency.Proficiencies;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency.Proficiencies.Proficiency;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Dice;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Formalizer.Roll;

public class ProficiencyLogic implements CharacterLogic {

	private Proficiencies proficiencies;
	@Override
	public void initialize(CharacterDnd character) {
		proficiencies = character.getAbility().getProficiencies();
	}
	
	public int getProf(Proficiency prof) {
		switch (prof) {
		case BASE:
			return proficiencies.getProficiency();
		case COMPETENSE:
			return proficiencies.getProficiency() * 2;
		case HALF:
			return proficiencies.getProficiency() / 2;
		default:
			return 0;
		}
	}
	
	public Dice getDice(Proficiency prof) {
		Dice answer = new Dice("Proficiency", 0, Roll.NO_ROLL);
		switch(prof) {
		case HALF:
			answer.setBuff(proficiencies.getProficiency() / 2);
			break;
		case BASE:
			answer.setBuff(proficiencies.getProficiency());
			break;
		case COMPETENSE:
			answer.setBuff(proficiencies.getProficiency() * 2);
			break;
		}
		return answer;
	}
	
	public Proficiency getProf(String name) {
		for(Possession target: proficiencies.getPossessions()) {
			if(target.getName().contains(name)) {
				return target.getProf();
			}
		}
		return null;
	}

	public void add(Possession possession) {
		for(Possession target: proficiencies.getPossessions()) {
			if(target.getName().contains(possession.getName())) {
				target.setProf(upOrStay(target.getProf(), possession.getProf()));
				return;
			}
		}
		proficiencies.getPossessions().add(possession);
	}
	
	private Proficiency upOrStay(Proficiency first, Proficiency second) {
		if(second.equals(Proficiency.COMPETENSE)) {
			return Proficiency.COMPETENSE;
		} else if(first.equals(Proficiency.BASE)) {
			return Proficiency.BASE;
		} else {
			return second;
		}
	}
	
	public void add(String name) {
		for(Possession target: proficiencies.getPossessions()) {
			if(target.getName().equalsIgnoreCase(name)) {
				return;
			}
		}
		proficiencies.getPossessions().add(new Possession(name));
	}
	
	public boolean holded(String name) {
		for(Possession target: proficiencies.getPossessions()) {
			if(target.getName().contains(name)) {
				return true;
			}
		}
		return false;
	}
	
}
