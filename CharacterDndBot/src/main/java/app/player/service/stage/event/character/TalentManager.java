package app.player.service.stage.event.character;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.features.Feature;
import app.dnd.model.ability.proficiency.Possession;
import app.dnd.model.ability.spells.Spell;
import app.dnd.model.actions.Action;
import app.dnd.service.logic.DndFacade;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.player.service.stage.EventExecutor;
import app.player.service.stage.event.UserExecutor;
import app.user.model.User;

@EventExecutor(Location.TALANT)
public class TalentManager implements UserExecutor {

	@Autowired
	private TalentExecutor talentManager;

	@Override
	public void executeFor(User user) {

		Action action = (Action) user.getStage();
		if (action.condition() == 0 && action.getObjectDnd() == null) {
			talentManager.menu(user);

		} else if (action.getObjectDnd() == null) {
			if(action.condition() == 1) {
				talentManager.profMenu(user);

			} else if (action.condition() == 2) {
				talentManager.startAddProff(user);

			} else if (action.condition() == 3) {
				if(action.getAnswers()[2].equals(Button.RETURN_TO_TALANT.NAME)) {
					talentManager.returnToAbility(user);

				} else if(action.getAnswers()[2].equals(Button.HINT_LIST.NAME)) {
					talentManager.hintList(user);

				} else {
					talentManager.endAddProff(user);
				}
			} 	

		} else if (action.getAnswers()[0].equals(Button.SPELL.NAME)) {
			talentManager.spellMenu(user);

		} else if (action.getAnswers()[0].equals(Button.FEATURE.NAME)) {
			talentManager.featureMenu(user);

		} else if (action.getObjectDnd() instanceof Feature) {
			talentManager.featureTarget(user);
			
		} else if (action.getObjectDnd() instanceof Spell) {
			talentManager.spellTarget(user);
			
		} else {
			user.setAct(ReturnAct.builder().target(Button.MENU.NAME).build());
		}
	}
}

@Component
class TalentExecutor {

	@Autowired
	private DndFacade dndFacade;

	void menu(User user) {
		user.setAct(ReturnAct.builder()
				.target(Button.MENU.NAME)
				.act(SingleAct.builder()
						.name(Button.TALENTS.NAME)
						.action(dndFacade.action().talants().menu(user.getId()))
						.build())
				.build());
	}

	void profMenu(User user) {
		user.setAct(ReturnAct.builder()
				.target(Button.TALENTS.NAME)
				.act(SingleAct.builder()
						.name(Button.POSSESSION.NAME)
						.action(dndFacade.action().talants().prof().menu(user.getStage(), user.getId()))
						.build())
				.build());
	}

	void startAddProff(User user) {
		user.setAct(SingleAct.builder()
				.name("addPossession")
				.action(dndFacade.action().talants().prof().create(user.getStage()))
				.build());
	}

	void endAddProff(User user) {
		Action action = (Action) user.getStage();
		dndFacade.hero().talants().proficiencies().addPossession(user.getId(), new Possession(action.getAnswers()[2]));
		user.setAct(ReturnAct.builder()
				.target(Button.TALENTS.NAME)
				.call(Button.POSSESSION.NAME)
				.build());
	}

	void returnToAbility(User user) {
		user.setAct(ReturnAct.builder()
				.target(Button.MENU.NAME)
				.call(Button.TALENTS.NAME)
				.build());
	}

	void hintList(User user) {
		user.setAct(SingleAct.builder()
				.name(Button.HINT_LIST.NAME)
				.text(dndFacade.information().talants().proficiencies().hintList())
				.build());
	}

	void spellMenu(User user) {
		user.setAct(ReturnAct.builder()
				.target(Button.TALENTS.NAME)
				.act(SingleAct.builder()
						.name(Button.SPELL.NAME)
						.action(dndFacade.action().talants().magic().menu(user.getStage(), user.getId()))
						.build())
				.build());
	}

	void featureMenu(User user) {
		user.setAct(ReturnAct.builder()
				.target(Button.TALENTS.NAME)
				.act(SingleAct.builder()
						.name(Button.FEATURE.NAME)
						.action(dndFacade.action().talants().feature().menu(user.getStage(), user.getId()))
						.build())
				.build());
	}

	void featureTarget(User user) {
		user.setAct(SingleAct.builder()
				.name("FeatureTarget")
				.action(dndFacade.action().talants().feature().targetFeature(user.getStage()))
				.build());
	}

	void spellTarget(User user) {
		user.setAct(SingleAct.builder()
				.name("SpellTarget")
				.action(dndFacade.action().talants().magic().targetSpell(user.getStage()))
				.build());
	}
}





