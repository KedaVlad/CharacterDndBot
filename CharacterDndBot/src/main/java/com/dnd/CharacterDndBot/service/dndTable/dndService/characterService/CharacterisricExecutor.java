package com.dnd.CharacterDndBot.service.dndTable.dndService.characterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ArrayActsBuilder;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.acts.actions.BaseAction;
import com.dnd.CharacterDndBot.service.acts.actions.PoolActions;
import com.dnd.CharacterDndBot.service.acts.actions.PreRoll;
import com.dnd.CharacterDndBot.service.acts.actions.RollAction;
import com.dnd.CharacterDndBot.service.bot.user.User;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency.Possession;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency.Proficiencies.Proficiency;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.SaveRoll;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Skill;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Stat;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Location;
import com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap.characteristic.SaveRollsButtonsBuilder;
import com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap.characteristic.SkillButtonsBuilder;
import com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap.characteristic.StatUp;
import com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap.proficiencies.ProficienciesAddPossession;

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

	@Override
	public Act executeFor(Action action, User user) {
		return ReturnAct.builder()
				.target(ABILITY_B)
				.act(SingleAct.builder()
						.name(CHARACTERISTIC_B)
						.text(InformatorFactory.build(user.getCharactersPool().getActual().getCharacteristics()).getInformation())
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

	@Override
	public Act executeFor(Action action, User user) {
		Stat[] stats = user.getCharactersPool().getActual().getCharacteristics().getStats();
		BaseAction[][] pool = new BaseAction[][] {
			{
				Action.builder().name(stats[0].toString()).objectDnd(stats[0]).location(Location.CHARACTERISTIC).build(),
				Action.builder().name(stats[1].toString()).objectDnd(stats[1]).location(Location.CHARACTERISTIC).build()
			},{
				Action.builder().name(stats[2].toString()).objectDnd(stats[2]).location(Location.CHARACTERISTIC).build(),
				Action.builder().name(stats[3].toString()).objectDnd(stats[3]).location(Location.CHARACTERISTIC).build()
			},{
				Action.builder().name(stats[4].toString()).objectDnd(stats[4]).location(Location.CHARACTERISTIC).build(),
				Action.builder().name(stats[5].toString()).objectDnd(stats[5]).location(Location.CHARACTERISTIC).build()
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
		return ReturnAct.builder()
				.target(STAT_B)
				.act(ArrayActsBuilder.builder()
						.name("StatCase")
						.pool(SingleAct.builder()
								.name("Biba")
								.action(action)
								.text(stat.toString() + ". If u have reason to change value...")
								.build(),
								SingleAct.builder()
								.text("... or roll.")
								.name("Bebra")
								.action(PreRoll.builder()
										.roll(RollAction.buider()
												.statDepend(stat.getName())
												.build())
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
		int value = 0;
		switch(action.getAnswers()[0]) {
		case "+1":
			value = 1;
			break;
		case "+2":
			value = 2;
			break;
		case "+3":
			value = 3;
			break;
		case "-1":
			value = -1;
			break;
		case "-2":
			value = -2;
			break;
		case "-3":
			value = -3;
			break;
		}
		statUp.up(stat, value);
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
				Action.builder().name(saveRollButtons[1]).objectDnd(saveRolls[1]).location(Location.CHARACTERISTIC).build(),
				Action.builder().name(saveRollButtons[2]).objectDnd(saveRolls[2]).location(Location.CHARACTERISTIC).build()
			},{
				Action.builder().name(saveRollButtons[3]).objectDnd(saveRolls[3]).location(Location.CHARACTERISTIC).build(),
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

	@Override
	public Act executeFor(Action action, User user) {
		Skill article = (Skill) action.getObjectDnd();
		String[][] buttonsChange = buildSkillChangeButtons(article);
		String returnTo = SKILL_B;
		if(article instanceof SaveRoll) {
			returnTo = SAVE_ROLL_B;
		}
		if(buttonsChange == null) {
			return ReturnAct.builder()
					.target(returnTo)
					.act(SingleAct.builder()
							.name("ArticleCase")
							.text(article.getName())
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
									.text(article.getName() + ". If u have reasons up your possession of this...")
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