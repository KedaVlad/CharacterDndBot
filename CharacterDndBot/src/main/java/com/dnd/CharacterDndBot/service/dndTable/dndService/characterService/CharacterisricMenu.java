package com.dnd.CharacterDndBot.service.dndTable.dndService.characterService;

import com.dnd.CharacterDndBot.service.User;
import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ArrayActsBuilder;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.acts.actions.BaseAction;
import com.dnd.CharacterDndBot.service.acts.actions.PoolActions;
import com.dnd.CharacterDndBot.service.acts.actions.PreRoll;
import com.dnd.CharacterDndBot.service.acts.actions.RollAction;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency.Possession;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency.Proficiencies;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency.Proficiencies.Proficiency;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.SaveRoll;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Skill;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Stat;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Formalizer;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Location;
import com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap.LogicFactory;

import lombok.extern.slf4j.Slf4j;

public class CharacterisricMenu extends Executor<Action> {

	public CharacterisricMenu(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {

		if(action.getObjectDnd() == null) {
			switch(action.condition()) {
			case 0:
				return characteristic(user);
			case 1:
				return getMenu(user);
			default:
				return ReturnAct.builder().target(CHARACTERISTIC_B).call(CHARACTERISTIC_B).build();
			}
		} else {
			if(action.getObjectDnd() instanceof Stat)
			{
				return new StatMenu(action).executeFor(user);
			}
			else
			{
				return new SingleSkillExecutor(action).executeFor(user);
			}
		}
	}

	private Act characteristic(User user) {
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

	private Act getMenu(User user) {
		String targetMenu = action.getAnswers()[0];
		if(targetMenu.equals(STAT_B)) {
			return new StatMenu(action).executeFor(user);
		} else if(targetMenu.equals(SAVE_ROLL_B)) {
			return new SaveRollMenu(action).executeFor(user);
		} else if(targetMenu.equals(SKILL_B)) {
			return new StatMenu(action).executeFor(user);
		} else {
			return ReturnAct.builder().target(MENU_B).call(MENU_B).build();
		}
	}
}

@Slf4j
class StatMenu extends Executor<Action> {

	public StatMenu(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		if(action.getObjectDnd() == null) {
			return statMenu(user);
		} else if(action.condition() == 0) {
			return getStatCase();
		} else if(action.condition() == 1) {
			return changeStat(user);
		} else {
			log.error("StatMenu: condition error");
			return null;
		}
	}

	private Act statMenu(User user) { 
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

	private Act getStatCase() {
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

	private Act changeStat(User user) {
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
		LogicFactory.characteristic().initialize(user.getCharactersPool().getActual()).up(stat, value);
		return ReturnAct.builder().target(CHARACTERISTIC_B).call(STAT_B).build();
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


class SkillMenu extends Executor<Action> {

	public SkillMenu(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		return skillMenu(user);
	}

	private Act skillMenu(User user) {
		Skill[] skills = user.getCharactersPool().getActual().getCharacteristics().getSkills();
		Stat[] stats = user.getCharactersPool().getActual().getCharacteristics().getStats();
		Proficiencies prof = user.getCharactersPool().getActual().getAbility().getPossessions();
		BaseAction[][] pool = new BaseAction[skills.length/2][2];
		int j = 0;
		for(int i = 0; i < skills.length; i++) {
			if(i < 9) {
				pool[i][0] = Action.builder()
						.name(buildSkillButton(skills[i], stats, prof))
						.location(Location.CHARACTERISTIC)
						.objectDnd(skills[i])
						.build();
			} else {
				pool[j][1] = Action.builder()
						.name(buildSkillButton(skills[i], stats, prof))
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

	private String buildSkillButton(Skill skill, Stat[] stats, Proficiencies prof) {
		for (Stat stat : stats) {
			if (stat.getName() == skill.getDepends()) {
				String answer = Formalizer.modificator(stat.getValue()) + prof.getProf(skill.getProficiency()) + "";
				return answer + " " + skill.getName();
			}
		}
		return null;
	}
}

class SaveRollMenu extends Executor<Action> {

	public SaveRollMenu(Action action) {
		super(action); 
	}

	@Override
	public Act executeFor(User user) {
		return skillMenu(user);
	}

	private Act skillMenu(User user) { 

		Skill[] saveRolls = user.getCharactersPool().getActual().getCharacteristics().getSaveRolls();
		Stat[] stats = user.getCharactersPool().getActual().getCharacteristics().getStats();
		

		BaseAction[][] pool = new BaseAction[][] {
			{
				Action.builder().name(buildSkillButton(saveRolls[0], stats, prof)).objectDnd(saveRolls[0]).location(Location.CHARACTERISTIC).build(),
				Action.builder().name(buildSkillButton(saveRolls[1], stats, prof)).objectDnd(saveRolls[0]).location(Location.CHARACTERISTIC).build(),
				Action.builder().name(buildSkillButton(saveRolls[2], stats, prof)).objectDnd(saveRolls[0]).location(Location.CHARACTERISTIC).build()
			},{
				Action.builder().name(buildSkillButton(saveRolls[3], stats, prof)).objectDnd(saveRolls[0]).location(Location.CHARACTERISTIC).build(),
				Action.builder().name(buildSkillButton(saveRolls[4], stats, prof)).objectDnd(saveRolls[0]).location(Location.CHARACTERISTIC).build(),
				Action.builder().name(buildSkillButton(saveRolls[5], stats, prof)).objectDnd(saveRolls[0]).location(Location.CHARACTERISTIC).build()
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

	private String buildSkillButton(Skill skill, Stat[] stats, Proficiencies prof) {
		for(Stat stat: stats) {
			if(stat.getName() == skill.getDepends()) {
				String answer = (stat.getValue() - 10) / 2 + prof.getProf(skill.getProficiency()) + "";
				return answer + " " + skill.getName();
			}
		}
		return null;
	}
}


class SingleSkillExecutor extends Executor<Action> {

	public SingleSkillExecutor(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		if(action.condition() == 0) {
			return getArticleCase();
		} else {
			return changeArticle(user);
		}
	}

	private Act getArticleCase() {
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

	private Act changeArticle(User user) {
		Skill skill = (Skill) action.getObjectDnd();
		switch(action.getAnswers()[0]) {
		case "Up to COMPETENSE":
			skill.setProficiency(Proficiency.COMPETENSE);
			LogicFactory.proficiency().initialize(user.getCharactersPool().getActual()).add(new Possession(skill.getName(), Proficiency.COMPETENSE));
			break;
		case "Up to PROFICIENCY":
			skill.setProficiency(Proficiency.BASE);
			LogicFactory.proficiency().initialize(user.getCharactersPool().getActual()).add(new Possession(skill.getName()));
			break;
		} 
		if(skill instanceof SaveRoll) {
			return ReturnAct.builder().target(CHARACTERISTIC_B).call(SAVE_ROLL_B).build();
		} else {
			return ReturnAct.builder().target(CHARACTERISTIC_B).call(SKILL_B).build();
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
