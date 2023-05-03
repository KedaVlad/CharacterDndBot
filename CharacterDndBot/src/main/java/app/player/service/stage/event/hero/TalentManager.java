package app.player.service.stage.event.hero;


import app.player.model.event.StageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.telents.features.Feature;
import app.dnd.model.telents.proficiency.Possession;
import app.dnd.model.telents.spells.Spell;
import app.dnd.service.DndFacade;
import app.player.model.EventExecutor;
import app.player.model.Stage;
import app.player.model.act.Act;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.player.service.stage.Executor;
import app.bot.model.user.ActualHero;

@EventExecutor(Location.TALENT)
public class TalentManager implements Executor {

	@Autowired
	private TalentExecutor talentManager;

	@Override
	public Act execute(StageEvent event) {

		Action action = (Action) event.getTusk();
		if (action.condition() == 0 && action.getObjectDnd() == null) {
			return talentManager.menu(event.getUser().getActualHero());

		} else if (action.getObjectDnd() instanceof Feature) {
			return talentManager.featureTarget(event.getTusk());

		} else if (action.getObjectDnd() instanceof Spell) {
			return talentManager.spellTarget(event.getTusk());

		} else if (action.getAnswers()[0].equals(Button.POSSESSION.NAME)) {
			switch (action.condition()) {
				case 1 -> {
					return talentManager.profMenu(event.getUser().getActualHero(), event.getTusk());
				}
				case 2 -> {
					return talentManager.startAddProff(event.getTusk());
				}
				case 3 -> {
					if (action.getAnswers()[2].equals(Button.RETURN_TO_TALANT.NAME)) {
						return talentManager.returnToAbility();

					} else if (action.getAnswers()[2].equals(Button.HINT_LIST.NAME)) {
						return talentManager.hintList();

					} else {
						return talentManager.endAddProff(event.getUser().getActualHero(), event.getTusk());
					}
				}
				default -> {
					return ReturnAct.builder().target(Button.MENU.NAME).build();
				}
			}

		} else if (action.getAnswers()[0].equals(Button.SPELL.NAME)) {
			return talentManager.spellMenu(event.getUser().getActualHero(), event.getTusk());

		} else if (action.getAnswers()[0].equals(Button.FEATURE.NAME)
				|| action.getAnswers()[0].equals(Button.TRAIT.NAME) 
				|| action.getAnswers()[0].equals(Button.FEAT.NAME)) {
			return talentManager.featureMenu(event.getUser().getActualHero(), event.getTusk());

		} else {
			return ReturnAct.builder().target(Button.MENU.NAME).build();
		}
	}
}

@Component
class TalentExecutor {

	@Autowired
	private DndFacade dndFacade;

	Act menu(ActualHero hero) {
		return ReturnAct.builder()
				.target(Button.MENU.NAME)
				.act(SingleAct.builder()
						.name(Button.TALENTS.NAME)
						.reply(true)
						.stage(dndFacade.action().talents().menu(hero))
						.build())
				.build();
	}

	Act profMenu(ActualHero hero, Stage stage) {
		return ReturnAct.builder()
				.target(Button.TALENTS.NAME)
				.act(SingleAct.builder()
						.reply(true)
						.name(Button.POSSESSION.NAME)
						.stage(dndFacade.action().talents().prof().menu(hero, stage))
						.build())
				.build();
	}

	Act startAddProff(Stage stage) {
		return SingleAct.builder()
				.name("addPossession")
				.reply(true)
				.mediator(true)
				.stage(dndFacade.action().talents().prof().create(stage))
				.build();
	}

	Act endAddProff(ActualHero hero, Stage stage) {
		Action action = (Action) stage;
		dndFacade.hero().talants().proficiencies().addPossession(hero, new Possession(action.getAnswers()[2]));
		return ReturnAct.builder()
				.target(Button.TALENTS.NAME)
				.call(Button.POSSESSION.NAME)
				.build();
	}

	Act returnToAbility() {
		return ReturnAct.builder()
				.target(Button.MENU.NAME)
				.call(Button.TALENTS.NAME)
				.build();
	}

	Act hintList() {
		return SingleAct.builder()
				.name(Button.HINT_LIST.NAME)
				.stage(dndFacade.action().talents().prof().hintList())
				.build();
	}

	Act spellMenu(ActualHero hero, Stage stage) {
		return ReturnAct.builder()
				.target(Button.TALENTS.NAME)
				.act(SingleAct.builder()
						.name(Button.SPELL.NAME)
						.stage(dndFacade.action().talents().magic().menu(hero, stage))
						.build())
				.build();
	}

	Act featureMenu(ActualHero hero, Stage stage) {
		return ReturnAct.builder()
				.target(Button.TALENTS.NAME)
				.act(SingleAct.builder()
						.name(Button.FEATURE.NAME)
						.stage(dndFacade.action().talents().feature().menu(hero, stage))
						.build())
				.build();
	}

	Act featureTarget(Stage stage) {
		return SingleAct.builder()
				.name("FeatureTarget")
				.stage(dndFacade.action().talents().feature().targetFeature(stage))
				.build();
	}

	Act spellTarget(Stage stage) {
		return SingleAct.builder()
				.name("SpellTarget")
				.stage(dndFacade.action().talents().magic().targetSpell(stage))
				.build();
	}
}





