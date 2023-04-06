package app.player.service.stage.event.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.actions.PoolActions;
import app.dnd.model.actions.PreRoll;
import app.dnd.model.actions.RollAction;
import app.dnd.model.characteristics.SaveRoll;
import app.dnd.model.characteristics.Skill;
import app.dnd.model.characteristics.Stat;
import app.dnd.model.enums.Proficiency;
import app.dnd.service.logic.DndFacade;
import app.player.model.act.Act;
import app.player.model.act.ArrayActsBuilder;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.player.service.stage.EventExecutor;
import app.player.service.stage.Executor;
import app.player.service.stage.event.UserExecutor;
import app.user.model.User;
import lombok.extern.slf4j.Slf4j;

@EventExecutor(Location.ABILITY)
public class AbilityManager implements UserExecutor {

	@Autowired
	private AbilityExecutor abilityExecutor;

	@Override
	public void executeFor(User user) {

		Action action = (Action) user.getStage();


		if(action.condition() == 0) {
			if (action.getObjectDnd() instanceof Stat) {
				abilityExecutor.singleStatMenu(user);
			} else if (action.getObjectDnd() instanceof Skill) {
				abilityExecutor.singleSkillMenu(user);
			} else if (action.getObjectDnd() instanceof SaveRoll) {
				abilityExecutor.singleSaveRollMenu(user);
			} else { 
				abilityExecutor.abilityMenu(user);
			}

		} else if(action.condition() == 1) {

			String targetMenu = action.getAnswers()[0];
			if(targetMenu.equals(Button.STAT.NAME)) {
				statMenu.executeFor(action, user);
			} else if(targetMenu.equals(Button.SAVE_ROLL.NAME)) {
				saveRollMenu.executeFor(action, user);
			} else if(targetMenu.equals(Button.SKILL.NAME)) {
				skillMenu.executeFor(action, user);
			} else {
				abilityExecutor.changeStat(user);
			}
		}
	}

	private Act getMenu(Action action, User user) {
		String targetMenu = action.getAnswers()[0];
		if(targetMenu.equals(Button.STAT.NAME)) {
			return statExecutor.executeFor(action, user);
		} else if(targetMenu.equals(Button.SAVE_ROLL.NAME)) {
			return saveRollExecutor.executeFor(action, user);
		} else if(targetMenu.equals(Button.SKILL.NAME)) {
			return skillExecutor.executeFor(action, user);
		} else {
			return ReturnAct.builder().target(ButtonName.MENU_B).call(ButtonName.MENU_B).build();
		}
	}
}


@Component
class AbilityExecutor {

	@Autowired
	private DndFacade dndFacade;

	public void abilityMenu(User user) {
		user.setAct(ReturnAct.builder()
				.target(Button.MENU.NAME)
				.act(SingleAct.builder()
						.name(Button.ABILITY.NAME)
						.action(dndFacade.action().ability().menu())
						.build())
				.build());
	}	
	
	public void statMenu(User user) {
		
		user.setAct(ReturnAct.builder()
		.target(Button.ABILITY.NAME)
		.act(SingleAct.builder()
				.name(Button.STAT.NAME)
				.text(dndFacade.information().characteristic().stat().menu())
				.action(dndFacade.action().ability().stat().menu(user.getId()))
				.build())
		.build());
	}

	public void singleStatMenu(User user) {
		
		user.setAct(ArrayActsBuilder.builder()
				.name("StatCase")
				.pool(SingleAct.builder()
						.name("ChangeValue")
						.action(dndFacade.action().ability().singleStat(user.getStage()))
						.build(),
						SingleAct.builder()
						.name("Roll")
						.action(dndFacade.action().ability().preRoll(user.getStage()))
						.build())
				.build());
	}	

	public void changeStat(User user) {
		Action action = (Action) user.getStage();
		Stat stat = (Stat) action.getObjectDnd();
		int value = Integer.parseInt(action.getAnswers()[0]);
		dndFacade.hero().characteristic().stat().up(user.getId(), stat.getName(), value);
		user.setAct(ReturnAct.builder()
				.target(Button.ABILITY.NAME)
				.call(Button.STAT.NAME)
				.build());
	}



	public void singleSaveRollMenu(User user) {
		// TODO Auto-generated method stub

	}


	public void singleSkillMenu(User user) {
		// TODO Auto-generated method stub

	}

}





@Slf4j
@Component
class SkillExecutor implements Executor<Action> {

	@Autowired
	private SkillMenu skillMenu;
	@Autowired
	private SingleSkillMenu singleSkillMenu;
	@Autowired
	private ChangeSkill changeSkill;

	@Override
	public Act executeFor(Action action, User user) {
		if(action.getObjectDnd() == null) {
			return skillMenu.executeFor(action, user);
		} else if(action.condition() == 0) {
			return singleSkillMenu.executeFor(action, user);
		} else if(action.condition() == 1) {
			return changeSkill.executeFor(action, user);
		} else {
			log.error("SkillExecutor: condition error");
			return null;
		}
	}
}



@Component
class SkillMenu implements Executor<Action> {

	@Autowired
	private DndFacade dndFacade;

	@Override
	public Act executeFor(Action action, User user) {

		return ReturnAct.builder()
				.target(ButtonName.CHARACTERISTIC_B)
				.act(SingleAct.builder()
						.name(ButtonName.SKILL_B)
						.text(dndFacade.information().characteristic().skill().menu())
						.action(PoolActions.builder()
								.actionsPool(dndFacade.buttons().characteristic().skill().menu(user.getId()))
								.build())
						.build())
				.build();			
	}
}

@Component
class SingleSkillMenu implements Executor<Action> {

	@Autowired
	private DndFacade dndFacade;

	@Override
	public Act executeFor(Action action, User user) {

		Skill article = (Skill) action.getObjectDnd();
		String[][] buttonsChange = dndFacade.buttons().characteristic().skill().targetChange(article);

		if(buttonsChange == null) {
			return ReturnAct.builder()
					.target(ButtonName.SKILL_B)
					.act(SingleAct.builder()
							.name("ArticleCase")
							.text(dndFacade.information().characteristic().skill().target(article))
							.action(PreRoll.builder()
									.roll(RollAction.buider()
											.statDepend(article.getCore().getStat())
											.proficiency(article.getProficiency())
											.build())
									.build())
							.build())
					.build();
		} else {
			action.setButtons(buttonsChange);
			return ReturnAct.builder()
					.target(ButtonName.SKILL_B)
					.act(ArrayActsBuilder.builder()
							.name("ArticleCase")
							.pool(SingleAct.builder()
									.action(action)
									.text(dndFacade.information().characteristic().skill().target(article) + ". If u have reasons up your possession of this...")
									.build(),
									SingleAct.builder()
									.text("... or roll.")
									.action(PreRoll.builder()
											.roll(RollAction.buider()
													.statDepend(article.getCore().getStat())
													.proficiency(article.getProficiency())
													.build())
											.build())
									.build())
							.build())
					.build();
		}
	}

}

@Component
class ChangeSkill implements Executor<Action> { 

	@Autowired
	private DndFacade dndFacade;

	@Override
	public Act executeFor(Action action, User user) {
		Skill skill = (Skill) action.getObjectDnd();
		switch(action.getAnswers()[0]) {
		case "Up to COMPETENSE":
			skill.setProficiency(Proficiency.COMPETENSE);
			dndFacade.hero().characteristic().skill().up(skill, user.getId());
			break;
		case "Up to PROFICIENCY":
			skill.setProficiency(Proficiency.BASE);
			dndFacade.hero().characteristic().skill().up(skill, user.getId());
			break;
		} 

		return ReturnAct.builder().target(ButtonName.CHARACTERISTIC_B).call(ButtonName.SKILL_B).build();
	}
}

@Slf4j
@Component
class SaveRollExecutor implements Executor<Action> {

	@Autowired
	private SaveRollMenu saveRollMenu;
	@Autowired
	private SingleSaveRollMenu singleSaveRollMenu;
	@Autowired
	private ChangeSaveRoll changeSaveRoll;

	@Override
	public Act executeFor(Action action, User user) {
		if(action.getObjectDnd() == null) {
			return saveRollMenu.executeFor(action, user);
		} else if(action.condition() == 0) {
			return singleSaveRollMenu.executeFor(action, user);
		} else if(action.condition() == 1) {
			return changeSaveRoll.executeFor(action, user);
		} else {
			log.error("SaveRollExecutor: condition error");
			return null;
		}
	}
}

@Component
class SaveRollMenu implements Executor<Action> {

	@Autowired
	private DndFacade dndFacade;

	@Override
	public Act executeFor(Action action, User user) {
		return ReturnAct.builder()
				.target(ButtonName.CHARACTERISTIC_B)
				.act(SingleAct.builder()
						.name(ButtonName.SAVE_ROLL_B)
						.text(dndFacade.information().characteristic().saveRoll().menu())
						.action(PoolActions.builder()
								.actionsPool(dndFacade.buttons().characteristic().saveRoll().menu(user.getId()))
								.build())
						.build())
				.build();			
	}
}

@Component
class SingleSaveRollMenu implements Executor<Action> {

	@Autowired
	private DndFacade dndFacade;

	@Override
	public Act executeFor(Action action, User user) {

		SaveRoll article = (SaveRoll) action.getObjectDnd();
		String[][] buttonsChange = dndFacade.buttons().characteristic().saveRoll().targetChange(article);

		if(buttonsChange == null) {
			return ReturnAct.builder()
					.target(ButtonName.SAVE_ROLL_B)
					.act(SingleAct.builder()
							.name("ArticleCase")
							.text(dndFacade.information().characteristic().saveRoll().target(article))
							.action(PreRoll.builder()
									.roll(RollAction.buider()
											.statDepend(article.getCore().getStat())
											.proficiency(article.getProficiency())
											.build())
									.build())
							.build())
					.build();
		} else {
			action.setButtons(buttonsChange);
			return ReturnAct.builder()
					.target(ButtonName.SAVE_ROLL_B)
					.act(ArrayActsBuilder.builder()
							.name("ArticleCase")
							.pool(SingleAct.builder()
									.action(action)
									.text(dndFacade.information().characteristic().saveRoll().target(article) + ". If u have reasons up your possession of this...")
									.build(),
									SingleAct.builder()
									.text("... or roll.")
									.action(PreRoll.builder()
											.roll(RollAction.buider()
													.statDepend(article.getCore().getStat())
													.proficiency(article.getProficiency())
													.build())
											.build())
									.build())
							.build())
					.build();
		}
	}

}

@Component
class ChangeSaveRoll implements Executor<Action> { 

	@Autowired
	private DndFacade dndFacade;

	@Override
	public Act executeFor(Action action, User user) {
		SaveRoll saveRoll = (SaveRoll) action.getObjectDnd();
		if(action.getAnswers()[0].equals("Up to PROFICIENCY")) {
			saveRoll.setProficiency(Proficiency.BASE);
			dndFacade.hero().characteristic().saveRoll().up(saveRoll, user.getId());
		} 

		return ReturnAct.builder().target(ButtonName.CHARACTERISTIC_B).call(ButtonName.SAVE_ROLL_B).build();
	}
}