package app.dnd.service.logic.talants.prof;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.proficiency.Possession;
import app.dnd.model.ability.proficiency.Proficiencies;
import app.dnd.model.enums.Armors;
import app.dnd.model.enums.Tools;
import app.dnd.model.enums.Weapons;

public interface ProficienciesInfo {

	public String possession(Long id);
	public String createInfo();
	public String hintList();
	
}

@Component
class ProficienciesInformator implements ProficienciesInfo {

	@Autowired
	private ProficienciesService proficienciesService;
	@Autowired
	private HintList hintList;
	
	@Override
	public String possession(Long id) {
		Proficiencies proficiencies = proficienciesService.getById(id);
		String text = "This is your possessions. \n";
		for(Possession possession: proficiencies.getPossessions()) {
			text += possession.toString() + "\n";
		}
		return  text;
	}

	@Override
	public String createInfo() {
	
		return "If you want to add possession of some Skill/Save roll from characteristics - you can do it right by using this pattern (CHARACTERISTIC > SKILLS > Up to proficiency)\n"
				+ "If it`s possession of Weapon/Armor you shood to write ritht the type(correct spelling in Hint list)\n"
				+ "But if it concerns something else(language or metier) write as you like.";
	}

	@Override
	public String hintList() {
		return hintList.build();
	}
}


@Component
class HintList {

	public String build() {
		
		String answer = "";
		answer += "Weapon: ";
		for (int i = 0; i < Weapons.values().length; i++) {
			if (i == Weapons.values().length - 1) {
				answer += Weapons.values()[i].toString() + ".";
			} else {
				answer += Weapons.values()[i].toString() + ", ";
			}
		}
		answer += "\nTool: ";
		for (int i = 0; i < Tools.values().length; i++) {
			if (i == Tools.values().length - 1) {
				answer += Tools.values()[i].toString() + ".";
			} else {
				answer += Tools.values()[i].toString() + ", ";
			}
		}
		answer += "\nArmor: ";
		for (int i = 0; i < Armors.values().length; i++) {
			if (i == Armors.values().length - 1) {
				answer += Armors.values()[i].toString() + ".";
			} else {
				answer += Armors.values()[i].toString() + ", ";
			}
		}
		return answer;
	}
}