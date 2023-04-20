package app.dnd.service.talants.stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.dnd.model.enums.Armors;
import app.dnd.model.enums.Tools;
import app.dnd.model.enums.Weapons;
import app.dnd.service.talants.logic.ProficienciesLogic;
import app.player.model.Stage;
import app.player.model.enums.Button;
import app.user.model.ActualHero;

public interface ProfAction {
	
	BaseAction menu(ActualHero hero, Stage stage);
	BaseAction create(Stage stage);
	Stage hintList();
}

@Component
class ProfActor implements ProfAction {

	@Autowired
	private ProficienciesLogic proficienciesLogic;
	
	@Override
	public BaseAction menu(ActualHero hero, Stage stage) {

		Action action = (Action) stage;
		action.setButtons(new String[][]{{"Add possession"},{Button.RETURN_TO_MENU.NAME}});
		action.setText(proficienciesLogic.profMenu(hero));
		return action;
	}

	@Override
	public BaseAction create(Stage stage) {
		
		Action action = (Action) stage;
		action.setButtons(new String[][] {{"Hint list"},{"Return to abylity"}});
		action.setText("If you want to add possession of some Skill/Save roll from characteristics - you can do it right by using this pattern (CHARACTERISTIC > SKILLS > Up to proficiency)\n"
				+ "If it`s possession of Weapon/Armor you shood to write ritht the type(correct spelling in Hint list)\n"
				+ "But if it concerns something else(language or metier) write as you like.");
		return action;
	}

	@Override
	public Stage hintList() {
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
		return Action.builder().text(answer).build();
	}


}


