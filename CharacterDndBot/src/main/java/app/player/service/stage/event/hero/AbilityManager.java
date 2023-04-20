package app.player.service.stage.event.hero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.SaveRoll;
import app.dnd.model.ability.Skill;
import app.dnd.model.ability.Stat;
import app.dnd.model.actions.Action;
import app.dnd.model.actions.PreRoll;
import app.dnd.model.enums.Proficiency;
import app.dnd.service.DndFacade;
import app.player.event.UserEvent;
import app.player.model.EventExecutor;
import app.player.model.Stage;
import app.player.model.act.Act;
import app.player.model.act.ArrayActsBuilder;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.player.service.stage.Executor;
import app.user.model.ActualHero;

@EventExecutor(Location.ABILITY)
public class AbilityManager implements Executor {

	@Autowired
	private AbilityExecutor abilityExecutor;

	@Override
	public Act execute(UserEvent<Stage> event) {

		if(event.getTask() instanceof Action) {
			Action action = (Action) event.getTask();

			if(action.condition() == 0) {
				if (action.getObjectDnd() instanceof Stat) {
					return abilityExecutor.singleStatMenu(event.getTask());
				} else if (action.getObjectDnd() instanceof Skill) {
					return abilityExecutor.singleSkillMenu(event.getTask());
				} else if (action.getObjectDnd() instanceof SaveRoll) {
					return abilityExecutor.singleSaveRollMenu(event.getTask());
				} else { 
					return abilityExecutor.abilityMenu();
				}

			} else if(action.condition() == 1) {

				String targetMenu = action.getAnswers()[0];
				if(targetMenu.equals(Button.STAT.NAME)) {
					return abilityExecutor.statMenu(event.getUser().getActualHero());
				} else if(targetMenu.equals(Button.SAVE_ROLL.NAME)) {
					return abilityExecutor.saveRollMenu(event.getUser().getActualHero());
				} else if(targetMenu.equals(Button.SKILL.NAME)) {
					return abilityExecutor.skillMenu(event.getUser().getActualHero());
				} else {
					return abilityExecutor.changeStat(event.getUser().getActualHero(), event.getTask());
				}
			} else {
				return ReturnAct.builder().target(Button.MENU.NAME).build();
			}		

		} else if(event.getTask() instanceof PreRoll) {
			return abilityExecutor.preRoll(event.getUser().getActualHero(), (PreRoll) event.getTask());
		} else {
			return ReturnAct.builder().target(Button.MENU.NAME).build();
		}
	}
}


@Component
class AbilityExecutor {

	@Autowired
	private DndFacade dndFacade;

	Act abilityMenu() {
		return ReturnAct.builder()
				.target(Button.MENU.NAME)
				.act(SingleAct.builder()
						.name(Button.ABILITY.NAME)
						.stage(dndFacade.action().ability().menu())
						.build())
				.build();
	}	

	Act statMenu(ActualHero hero) {
		return ReturnAct.builder()
				.target(Button.ABILITY.NAME)
				.act(SingleAct.builder()
						.name(Button.STAT.NAME)
						.stage(dndFacade.action().ability().stat().menu(hero))
						.build())
				.build();
	}

	Act singleStatMenu(Stage stage) {
		return ArrayActsBuilder.builder()
				.name("StatCase")
				.pool(dndFacade.action().ability().stat().singleStat(stage), 
						dndFacade.action().ability().buildAbilityPreRoll((Action) stage))		
				.build();
	}	

	Act changeStat(ActualHero hero, Stage stage) {
		Action action = (Action) stage;
		Stat stat = (Stat) action.getObjectDnd();
		int value = Integer.parseInt(action.getAnswers()[0]);
		dndFacade.hero().ability().stat().up(hero, stat.getCore(), value);
		return ReturnAct.builder()
				.target(Button.ABILITY.NAME)
				.call(Button.STAT.NAME)
				.build();
	}

	Act saveRollMenu(ActualHero actualHero) {
		return ReturnAct.builder()
				.target(Button.ABILITY.NAME)
				.act(SingleAct.builder()
						.name(Button.SAVE_ROLL.NAME)
						.stage(dndFacade.action().ability().saveRoll().menu(actualHero))
						.build())
				.build();
	}

	Act singleSaveRollMenu(Stage stage) {

		Action action = (Action) stage;
		SaveRoll article = (SaveRoll) action.getObjectDnd();
		
		if(dndFacade.hero().ability().saveRoll().maximum(article)) {
			return ReturnAct.builder()
					.target(Button.SAVE_ROLL.NAME)
					.act(SingleAct.builder()
							.name("ArticleCase")
							.stage(dndFacade.action().ability().buildAbilityPreRoll((Action) stage))
							.build())
					.build();
		} else {
			return ReturnAct.builder()
					.target(Button.SAVE_ROLL.NAME)
					.act(ArrayActsBuilder.builder()
							.name("ArticleCase")
							.pool(dndFacade.action().ability().saveRoll().singleSaveRoll(article),
									dndFacade.action().ability().buildAbilityPreRoll((Action) stage))
							.build())
					.build();
		}
	}

	Act changeSaveRoll(ActualHero actualHero, Stage stage) {

		Action action = (Action) stage;
		SaveRoll saveRoll = (SaveRoll) action.getObjectDnd();
		if(action.getAnswers()[0].equals("Up to PROFICIENCY")) {
			saveRoll.setProficiency(Proficiency.BASE);
			dndFacade.hero().ability().saveRoll().change(actualHero, saveRoll);
		} 

		return ReturnAct.builder().target(Button.ABILITY.NAME).call(Button.SAVE_ROLL.NAME).build();
	}

	Act skillMenu(ActualHero hero) {
		
		return ReturnAct.builder()
				.target(Button.ABILITY.NAME)
				.act(SingleAct.builder()
						.name(Button.SKILL.NAME)
						.stage(dndFacade.action().ability().skill().menu(hero))
						.build())
				.build();
	}

	Act singleSkillMenu(Stage stage) {

		Action action = (Action) stage;
		Skill article = (Skill) action.getObjectDnd();
		
		if(dndFacade.hero().ability().skill().maximum(article)) {
			return ReturnAct.builder()
					.target(Button.SAVE_ROLL.NAME)
					.act(SingleAct.builder()
							.name("ArticleCase")
							.stage(dndFacade.action().ability().buildAbilityPreRoll((Action) stage))
							.build())
					.build();
		} else {
			return ReturnAct.builder()
					.target(Button.SAVE_ROLL.NAME)
					.act(ArrayActsBuilder.builder()
							.name("ArticleCase")
							.pool(dndFacade.action().ability().skill().singleSkill(article),
									dndFacade.action().ability().buildAbilityPreRoll((Action) stage))
							.build())
					.build();
		}
	}

	Act changeSkill(ActualHero actualHero, Stage stage) {

		Action action = (Action) stage;
		Skill skill = (Skill) action.getObjectDnd();
		switch(action.getAnswers()[0]) {
		case "Up to COMPETENSE":
			skill.setProficiency(Proficiency.COMPETENSE);
			dndFacade.hero().ability().skill().up(actualHero, skill);
			break;
		case "Up to PROFICIENCY":
			skill.setProficiency(Proficiency.BASE);
			dndFacade.hero().ability().skill().up(actualHero, skill);
			break;
		} 

		return ReturnAct.builder().target(Button.ABILITY.NAME).call(Button.SKILL.NAME).build();
	}

	Act preRoll(ActualHero actualHero, PreRoll preRoll) {

		return SingleAct.builder()
				.name("Ability roll")
				.stage(dndFacade.roll().compleatPreRoll(actualHero, preRoll))
				.build();
	}

}
