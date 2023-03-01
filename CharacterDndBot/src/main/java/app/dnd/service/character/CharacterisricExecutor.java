package app.dnd.service.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.bot.model.act.Act;
import app.bot.model.act.ArrayActsBuilder;
import app.bot.model.act.ReturnAct;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.act.actions.BaseAction;
import app.bot.model.act.actions.PoolActions;
import app.bot.model.act.actions.PreRoll;
import app.bot.model.act.actions.RollAction;
import app.bot.model.user.User;
import app.bot.service.ActualHeroService;
import app.dnd.dto.ability.proficiency.Proficiencies.Proficiency;
import app.dnd.dto.characteristics.SaveRoll;
import app.dnd.dto.characteristics.Skill;
import app.dnd.dto.characteristics.Stat;
import app.dnd.service.Executor;
import app.dnd.service.Location;
import app.dnd.service.logic.characteristic.SaveRollsButtonsBuilder;
import app.dnd.service.logic.characteristic.SkillButtonsBuilder;
import app.dnd.service.logic.characteristic.SkillSingleButtonBuilder;
import app.dnd.service.logic.characteristic.SkillUp;
import app.dnd.service.logic.characteristic.StatButtonsBuilder;
import app.dnd.service.logic.characteristic.StatUp;
import lombok.extern.slf4j.Slf4j;

@Component
public class CharacterisricExecutor implements Executor<Action> {

	@Autowired
	private CharacteristicMenu characteristicMenu;
	@Autowired
	private StatExecutor statExecutor;
	@Autowired
	private SkillMenu skillMenu;
	@Autowired
	private SaveRollMenu saveRollMenu;
	@Autowired
	private SingleSkillExecutor singleSkillExecutor;
	
	@Override
	public Act executeFor(Action action, User user) {

		if(action.getObjectDnd() == null) {
			switch(action.condition()) {
			case 0:
				return characteristicMenu.executeFor(action, user);
			case 1:
				return getMenu(action, user);
			default:
				return ReturnAct.builder().target(CHARACTERISTIC_B).call(CHARACTERISTIC_B).build();
			}
		} else {
			if (action.getObjectDnd() instanceof Stat) {
				return statExecutor.executeFor(action, user);
			} else {
				return singleSkillExecutor.executeFor(action, user);
			}
		}
	}

	private Act getMenu(Action action, User user) {
		String targetMenu = action.getAnswers()[0];
		if(targetMenu.equals(STAT_B)) {
			return statExecutor.executeFor(action, user);
		} else if(targetMenu.equals(SAVE_ROLL_B)) {
			return saveRollMenu.executeFor(action, user);
		} else if(targetMenu.equals(SKILL_B)) {
			return skillMenu.executeFor(action, user);
		} else {
			return ReturnAct.builder().target(MENU_B).call(MENU_B).build();
		}
	}
}

@Component
class CharacteristicMenu implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
		return ReturnAct.builder()
				.target(MENU_B)
				.act(SingleAct.builder()
						.name(CHARACTERISTIC_B)
						.text("Here you can operate your abilities, rollig and changing value")
						.action(Action.builder()
								.location(Location.CHARACTERISTIC)
								.buttons(new String[][]{{STAT_B, SAVE_ROLL_B, SKILL_B},{RETURN_TO_MENU}})
								.replyButtons()
								.build())
						.build())
				.build();
	}
}

@Slf4j
@Component
class StatExecutor implements Executor<Action> {

	@Autowired
	private StatMenu statMenu;
	@Autowired
	private SingleStatMenu singleStatMenu;
	@Autowired
	private ChangeStat changeStat;

	@Override
	public Act executeFor(Action action, User user) {
		if(action.getObjectDnd() == null) {
			return statMenu.executeFor(action, user);
		} else if(action.condition() == 0) {
			return singleStatMenu.executeFor(action, user);
		} else if(action.condition() == 1) {
			return changeStat.executeFor(action, user);
		} else {
			log.error("StatMenu: condition error");
			return null;
		}
	}
}

@Component
class StatMenu implements Executor<Action> {

	@Autowired
	private StatButtonsBuilder statButtonsBuilder;

	@Override
	public Act executeFor(Action action, User user) {

		BaseAction[][] pool = statButtonsBuilder.build(user.getId());
		return ReturnAct.builder()
				.target(CHARACTERISTIC_B)
				.act(SingleAct.builder()
						.name(STAT_B)
						.text("Choose stat which you want to roll or change")
						.action(PoolActions.builder()
								.actionsPool(pool)
								.build())
						.build())
				.build();
	}

}

@Component
class SingleStatMenu implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
		Stat stat = (Stat) action.getObjectDnd();
		action.setButtons(buildStatChangeButtons(stat));
		return ArrayActsBuilder.builder()
				.name("StatCase")
				.pool(SingleAct.builder()
						.name("ChangeValue")
						.action(action)
						.text(stat.getName().toString() + " " + stat.getValue() + ". If u have reason to change value...")
						.build(),
						SingleAct.builder()
						.name("Roll")
						.text("... or roll.")
						.action(PreRoll.builder()
								.roll(RollAction.buider()
										.statDepend(stat.getName())
										.build())
								.build())
						.build())
				.build();	
	}

	public String[][] buildStatChangeButtons(Stat stat) {
		String[][] base = new String[][] {{"-3","-2","-1","+1","+2","+3"}};
		int max = stat.getMaxValue() - stat.getValue();
		int min = stat.getValue() - 3;
		if(max > 3) max = 3;
		if(min > 3) min = 3;
		String[][] answer = new String[1][min+max];
		if(answer[0].length == 6) {
			return base;
		} else if(min < max) {
			int j = 0;
			for(int i = 6 - answer[0].length; i < 6; i++) {
				answer[0][j] = base[0][i];
				j++;
			}
		} else {
			for(int i = 0; i < answer[0].length; i ++) {
				answer[0][i] = base[0][i];
			}
		}
		return answer;
	}
}

@Component
class ChangeStat implements Executor<Action> {

	@Autowired
	private StatUp statUp;

	@Override
	public Act executeFor(Action action, User user) {
		Stat stat = (Stat) action.getObjectDnd();
		int value = Integer.parseInt(action.getAnswers()[0]);
		statUp.up(user.getId(), stat.getName(), value);
		return ReturnAct.builder().target(CHARACTERISTIC_B).call(STAT_B).build();
	}	
}




@Component
class SkillMenu implements Executor<Action> {

	@Autowired
	private SkillButtonsBuilder skillButtonsBuilder;

	@Override
	public Act executeFor(Action action, User user) {

		BaseAction[][] pool = skillButtonsBuilder.build(user.getId());
		return ReturnAct.builder()
				.target(CHARACTERISTIC_B)
				.act(SingleAct.builder()
						.name(SKILL_B)
						.text("Choose skill which you want to roll or change")
						.action(PoolActions.builder()
								.actionsPool(pool)
								.build())
						.build())
				.build();			
	}
}

@Component
class SaveRollMenu implements Executor<Action> {

	@Autowired
	private SaveRollsButtonsBuilder saveRollsButtonsBuilder;

	@Override
	public Act executeFor(Action action, User user) {

		BaseAction[][] pool = saveRollsButtonsBuilder.build(user.getId());
		return ReturnAct.builder()
				.target(CHARACTERISTIC_B)
				.act(SingleAct.builder()
						.name(SAVE_ROLL_B)
						.text("Choose Save Roll which you want to roll or change")
						.action(PoolActions.builder()
								.actionsPool(pool)
								.build())
						.build())
				.build();			
	}
}

@Component
class SingleSkillExecutor implements Executor<Action> {

	@Autowired
	private ChangeSkillExecutor changeSkillExecutor;
	@Autowired
	private SingleSkillMenu singleSkillMenu;

	@Override
	public Act executeFor(Action action, User user) {
		if(action.condition() == 0) {
			return singleSkillMenu.executeFor(action, user);
		} else {
			return changeSkillExecutor.executeFor(action, user);
		}
	}
}

@Component
class SingleSkillMenu implements Executor<Action> {

	@Autowired
	private SkillSingleButtonBuilder skillSingleButtonBuilder;
	@Autowired
	private ActualHeroService actualHeroService;

	@Override
	public Act executeFor(Action action, User user) {
		Skill article = (Skill) action.getObjectDnd();
		String[][] buttonsChange = buildSkillChangeButtons(article);
		String returnTo = SKILL_B;
		String text = skillSingleButtonBuilder.build(article, actualHeroService.getById(user.getId()).getCharacter());
		if(article instanceof SaveRoll) {
			returnTo = SAVE_ROLL_B;
		}
		if(buttonsChange == null) {
			return ReturnAct.builder()
					.target(returnTo)
					.act(SingleAct.builder()
							.name("ArticleCase")
							.text(text)
							.action(PreRoll.builder()
									.roll(RollAction.buider()
											.statDepend(article.getDepends())
											.proficiency(article.getProficiency())
											.build())
									.build())
							.build())
					.build();
		} else {
			action.setButtons(buttonsChange);
			return ReturnAct.builder()
					.target(returnTo)
					.act(ArrayActsBuilder.builder()
							.name("ArticleCase")
							.pool(SingleAct.builder()
									.action(action)
									.text(text + ". If u have reasons up your possession of this...")
									.build(),
									SingleAct.builder()
									.text("... or roll.")
									.action(PreRoll.builder()
											.roll(RollAction.buider()
													.statDepend(article.getDepends())
													.proficiency(article.getProficiency())
													.build())
											.build())
									.build())
							.build())
					.build();
		}
	}

	private String[][] buildSkillChangeButtons(Skill article) {
		
		if(article.getProficiency() != null) {
			if(article.getProficiency().equals(Proficiency.BASE)) {
				return new String[][] {{"Up to COMPETENSE"}};
			} else if(article.getProficiency().equals(Proficiency.COMPETENSE)) {
				return null;
			} else {
				return new String[][] {{"Up to PROFICIENCY"}};
			}
		} else {
			return new String[][] {{"Up to PROFICIENCY"}};
		}
	}
}

@Component
class ChangeSkillExecutor implements Executor<Action> { 

	@Autowired
	private SkillUp skillUp;

	@Override
	public Act executeFor(Action action, User user) {
		Skill skill = (Skill) action.getObjectDnd();
		switch(action.getAnswers()[0]) {
		case "Up to COMPETENSE":
			skill.setProficiency(Proficiency.COMPETENSE);
			skillUp.up(user.getId(), skill);
			break;
		case "Up to PROFICIENCY":
			skill.setProficiency(Proficiency.BASE);
			skillUp.up(user.getId(), skill);
			break;
		} 
		if(skill instanceof SaveRoll) {
			return ReturnAct.builder().target(CHARACTERISTIC_B).call(SAVE_ROLL_B).build();
		} else {
			return ReturnAct.builder().target(CHARACTERISTIC_B).call(SKILL_B).build();
		}
	}

}