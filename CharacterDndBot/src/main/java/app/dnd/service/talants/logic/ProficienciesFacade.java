package app.dnd.service.talants.logic;

import app.dnd.model.enums.Roll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.enums.Proficiency;
import app.dnd.model.telents.proficiency.Possession;
import app.dnd.model.telents.proficiency.Proficiencies;
import app.dnd.service.talants.ProficienciesService;
import app.dnd.util.math.Dice;
import app.bot.model.user.ActualHero;

@Component
public class ProficienciesFacade implements ProficienciesLogic {
	
	@Autowired
	private ProficienciesService proficienciesService;
	
	@Override
	public void addPossession(ActualHero actualHero, Possession possession) {
		Proficiencies proficiencies = proficienciesService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		for (Possession target : proficiencies.getPossessions()) {
			if (target.getName().contains(possession.getName())) {
				target.setProf(upOrStay(target.getProf(), possession.getProf()));
				return;
			}
		}
		proficiencies.getPossessions().add(possession);
		proficienciesService.save(proficiencies);
	}
	
	private Proficiency upOrStay(Proficiency first, Proficiency second) {
		if (second.equals(Proficiency.COMPETENCE)) {
			return Proficiency.COMPETENCE;
		} else if (first.equals(Proficiency.BASE)) {
			return Proficiency.BASE;
		} else {
			return second;
		}
	}

	@Override
	public String profMenu(ActualHero actualHero) {
		Proficiencies proficiencies = proficienciesService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		String text = "This is your possessions. \n";
		for(Possession possession: proficiencies.getPossessions()) {
			text += possession.toString() + "\n";
		}
		return  text;
	}
	
	@Override
	public Dice buildDice(ActualHero actualHero, Proficiency proficiency) {
		
		Proficiencies proficiencies = proficienciesService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());

		Dice answer = new Dice("Proficiency", 0, Roll.NO_ROLL);
		switch(proficiency) {
		case HALF:
			answer.setBuff(proficiencies.getProficiency() / 2);
			break;
		case BASE:
			answer.setBuff(proficiencies.getProficiency());
			break;
		case COMPETENCE:
			answer.setBuff(proficiencies.getProficiency() * 2);
			break;
		}
		return answer;
	}

	@Override
	public void upProf(ActualHero actualHero, int lvl) {
		Proficiencies proficiencies = proficienciesService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		proficiencies.upProf(lvl);
		proficienciesService.save(proficiencies);
	}
}
