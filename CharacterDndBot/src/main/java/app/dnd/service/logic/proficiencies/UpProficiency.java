package app.dnd.service.logic.proficiencies;

import org.springframework.stereotype.Component;

import app.dnd.dto.CharacterDnd;
import app.dnd.dto.ability.proficiency.Proficiencies;

@Component
public class UpProficiency {

	public void build(CharacterDnd character) {
		Proficiencies prof = character.getAbility().getProficiencies();
		int lvl = character.getLvl().getLvl();
		if(lvl > 16) {
			prof.setProficiency(6);
		} else if (lvl > 12) {
			prof.setProficiency(5);
		} else if (lvl > 8) {
			prof.setProficiency(4);
		} else if (lvl > 4) {
			prof.setProficiency(3);
		} else {
			prof.setProficiency(2);
		}
	}
}
