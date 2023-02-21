package com.dnd.CharacterDndBot.dnd.service.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.bot.model.act.Act;
import com.dnd.CharacterDndBot.bot.model.act.ArrayActsBuilder;
import com.dnd.CharacterDndBot.bot.model.act.ReturnAct;
import com.dnd.CharacterDndBot.bot.model.act.SingleAct;
import com.dnd.CharacterDndBot.bot.model.act.actions.Action;
import com.dnd.CharacterDndBot.bot.model.act.actions.BaseAction;
import com.dnd.CharacterDndBot.bot.model.act.actions.PoolActions;
import com.dnd.CharacterDndBot.bot.model.act.actions.PreRoll;
import com.dnd.CharacterDndBot.bot.model.act.actions.RollAction;
import com.dnd.CharacterDndBot.bot.model.user.User;
import com.dnd.CharacterDndBot.dnd.dto.ability.proficiency.Possession;
import com.dnd.CharacterDndBot.dnd.dto.ability.proficiency.Proficiencies.Proficiency;
import com.dnd.CharacterDndBot.dnd.dto.characteristics.SaveRoll;
import com.dnd.CharacterDndBot.dnd.dto.characteristics.Skill;
import com.dnd.CharacterDndBot.dnd.dto.characteristics.Stat;
import com.dnd.CharacterDndBot.dnd.service.Executor;
import com.dnd.CharacterDndBot.dnd.service.Location;
import com.dnd.CharacterDndBot.dnd.service.logic.characteristic.SaveRollsButtonsBuilder;
import com.dnd.CharacterDndBot.dnd.service.logic.characteristic.SkillButtonsBuilder;
import com.dnd.CharacterDndBot.dnd.service.logic.characteristic.SkillSingleButtonBuilder;
import com.dnd.CharacterDndBot.dnd.service.logic.characteristic.StatButtonsBuilder;
import com.dnd.CharacterDndBot.dnd.service.logic.characteristic.StatUp;
import com.dnd.CharacterDndBot.dnd.service.logic.proficiencies.ProficienciesAddPossession;

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
			if(action.getObjectDnd() instanceof Stat)
			{
				return statExecutor.executeFor(action, user);
			}
			else
			{
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

	@Autowired
	private InformatorHandler InformatorHandlet;
	
	@Override
	public Act executeFor(Action action, User user) {
		return ReturnAct.builder()
				.target(MENU_B)
				.act(SingleAct.builder()
						.name(CHARACTERISTIC_B)
						.text(InformatorHandlet.handle(user.getCharactersPool().getActual().getCharacteristics()))
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
		Stat[] stats = user.getCharactersPool().getActual().getCharacteristics().getStats();
		String[] statButtons = statButtonsBuilder.build(user.getCharactersPool().getActual());
		BaseAction[][] pool = new BaseAction[][] {
			{
				Action.builder().name(statButtons[0]).objectDnd(stats[0]).location(Location.CHARACTERISTIC).build(),
				Action.builder().name(statButtons[1]).objectDnd(stats[1]).location(Location.CHARACTERISTIC).build()
			},{
				Action.builder().name(statButtons[2]).objectDnd(stats[2]).location(Location.CHARACTERISTIC).build(),
				Action.builder().name(statButtons[3]).objectDnd(stats[3]).location(Location.CHARACTERISTIC).build()
			},{
				Action.builder().name(statButtons[4]).objectDnd(stats[4]).location(Location.CHARACTERISTIC).build(),
				Action.builder().name(statButtons[5]).objectDnd(stats[5]).location(Location.CHARACTERISTIC).build()
			}};

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

@Slf4j
@Component
class ChangeStat implements Executor<Action> {

	@Autowired
	private StatUp statUp;

	@Override
	public Act executeFor(Action action, User user) {
		Stat stat = (Stat) action.getObjectDnd();
		int value = Integer.parseInt(action.getAnswers()[0]);

		log.info("ChangeStat value check: " + value );
		statUp.up(stat, value);
		log.info("ChangeStat return check: " + CHARACTERISTIC_B + " " + STAT_B );
		return ReturnAct.builder().target(CHARACTERISTIC_B).call(STAT_B).build();
	}	
}




@Component
class SkillMenu implements Executor<Action> {

	@Autowired
	private SkillButtonsBuilder skillButtonsBuilder;

	@Override
	public Act executeFor(Action action, User user) {
		Skill[] skills = user.getCharactersPool().getActual().getCharacteristics().getSkills();
		String[] skillButtons = skillButtonsBuilder.build(user.getCharactersPool().getActual());
		BaseAction[][] pool = new BaseAction[skills.length/2][2];
		int j = 0;
		for(int i = 0; i < skills.length; i++) {
			if(i < 9) {
				pool[i][0] = Action.builder()
						.name(skillButtons[i])
						.location(Location.CHARACTERISTIC)
						.objectDnd(skills[i])
						.build();
			} else {
				pool[j][1] = Action.builder()
						.name(skillButtons[i])
						.location(Location.CHARACTERISTIC)
						.objectDnd(skills[i])
						.build();
				j++;
			}
		}
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
	public Act executeFor(Action action,User user) {
		Skill[] saveRolls = user.getCharactersPool().getActual().getCharacteristics().getSaveRolls();
		String[] saveRollButtons = saveRollsButtonsBuilder.build(user.getCharactersPool().getActual());
		BaseAction[][] pool = new BaseAction[][] {
			{
				Action.builder().name(saveRollButtons[0]).objectDnd(saveRolls[0]).location(Location.CHARACTERISTIC).build(),
				Action.builder().name(saveRollButtons[1]).objectDnd(saveRolls[1]).location(Location.CHARACTERISTIC).build()
			},{
				Action.builder().name(saveRollButtons[2]).objectDnd(saveRolls[2]).location(Location.CHARACTERISTIC).build(),
				Action.builder().name(saveRollButtons[3]).objectDnd(saveRolls[3]).location(Location.CHARACTERISTIC).build()
			},{
				Action.builder().name(saveRollButtons[4]).objectDnd(saveRolls[4]).location(Location.CHARACTERISTIC).build(),
				Action.builder().name(saveRollButtons[5]).objectDnd(saveRolls[5]).location(Location.CHARACTERISTIC).build()
			}};
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
	
	@Override
	public Act executeFor(Action action, User user) {
		Skill article = (Skill) action.getObjectDnd();
		String[][] buttonsChange = buildSkillChangeButtons(article);
		String returnTo = SKILL_B;
		String text = skillSingleButtonBuilder.build(article, user.getCharactersPool().getActual());
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
	private ProficienciesAddPossession proficienciesAddPossession;

	@Override
	public Act executeFor(Action action, User user) {
		Skill skill = (Skill) action.getObjectDnd();
		switch(action.getAnswers()[0]) {
		case "Up to COMPETENSE":
			skill.setProficiency(Proficiency.COMPETENSE);
			proficienciesAddPossession.add(user.getCharactersPool().getActual().getAbility().getProficiencies(), new Possession(skill.getName(), Proficiency.COMPETENSE));
			break;
		case "Up to PROFICIENCY":
			skill.setProficiency(Proficiency.BASE);
			proficienciesAddPossession.add(user.getCharactersPool().getActual().getAbility().getProficiencies(), new Possession(skill.getName(), Proficiency.BASE));
			break;
		} 
		if(skill instanceof SaveRoll) {
			return ReturnAct.builder().target(CHARACTERISTIC_B).call(SAVE_ROLL_B).build();
		} else {
			return ReturnAct.builder().target(CHARACTERISTIC_B).call(SKILL_B).build();
		}
	}

}