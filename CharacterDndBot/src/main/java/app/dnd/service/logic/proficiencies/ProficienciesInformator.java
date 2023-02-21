package app.dnd.service.logic.proficiencies;

import org.springframework.stereotype.Component;

import app.dnd.dto.ability.proficiency.Possession;
import app.dnd.dto.ability.proficiency.Proficiencies;


@Component
public class ProficienciesInformator {

	public String info(Proficiencies proficiencies) {

		String text = "This is your possessions. \n";
		for(Possession possession: proficiencies.getPossessions())
		{
			text += possession.toString() + "\n";
		}
		return  text;
	}
}
