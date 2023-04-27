package app.dnd.service.ability.stage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.Ability;
import app.dnd.model.ability.Skill;
import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.dnd.model.actions.PoolActions;
import app.dnd.model.actions.PreRoll;
import app.dnd.model.actions.RollAction;
import app.dnd.model.actions.SingleAction;
import app.dnd.model.enums.Proficiency;
import app.dnd.model.enums.Skills;
import app.dnd.model.telents.proficiency.Proficiencies;
import app.dnd.service.ability.AbilityService;
import app.dnd.service.talants.ProficienciesService;
import app.dnd.util.ArrayToColumns;
import app.player.model.Stage;
import app.player.model.enums.Location;
import app.bot.model.user.ActualHero;

public interface SkillAction {
	
	Stage menu(ActualHero actualHero);
	
	BaseAction singleSkill(Skill skill);

	
}

@Component
class SkillActor implements SkillAction {
	
	@Autowired
	private SkillButtonBuilder saveRollsButtonBuilder;
	
	@Override
	public BaseAction menu(ActualHero actualHero) {
		return PoolActions.builder()
				.text("Choose skill which you want to roll or change")
				.actionsPool(saveRollsButtonBuilder.menu(actualHero.getId(), actualHero.getName()))
				.build();
	}
	
	@Override
	public BaseAction singleSkill(Skill skill) {
		
		return PreRoll.builder()
				.location(Location.ABILITY)
				.text(skill.getCore().getName())
				.roll(RollAction.buider()
						.statDepend(skill.getCore().getStat())
						.proficiency(skill.getProficiency())
						.build())
				.build();
	}

}

@Component
class SkillButtonBuilder {

	@Autowired
	private AbilityService abilityService;
	@Autowired
	private ArrayToColumns arrayToColumns;
	@Autowired
	private ProficienciesService proficienciesService;
	
	public SingleAction[][] menu(Long id, String name) {
		Ability characteristics = abilityService.findByIdAndOwnerName(id, name); 
		Proficiencies proficiencies = proficienciesService.findByIdAndOwnerName(id, name);
		Map<Skills, Skill> skills = characteristics.getSkills();
		SingleAction[] pool = new SingleAction[skills.keySet().size()];
		int i = 0;
		for(Skills skill: skills.keySet()) {
			pool[i] = Action.builder()
					.name((characteristics.modification(skills.get(skill).getCore().getStat()) + proficiencies.getProfBonus(skills.get(skill).getProficiency())) + " " + skill.getName())
					.objectDnd(skills.get(skill))
					.location(Location.ABILITY)
					.build();
			i++;
		}
		return arrayToColumns.rebuild(pool, 2, SingleAction.class);
	}

	public String[][] targetChange(Skill skill) {
		if(skill.getProficiency() != null) {
			if(skill.getProficiency().equals(Proficiency.BASE)) {
				return new String[][] {{"Up to COMPETENSE"}};
			} else if(skill.getProficiency().equals(Proficiency.COMPETENCE)) {
				return null;
			} else {
				return new String[][] {{"Up to PROFICIENCY"}};
			}
		} else {
			return new String[][] {{"Up to PROFICIENCY"}};
		}
	}
}