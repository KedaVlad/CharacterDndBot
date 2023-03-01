package app.dnd.service.logic.characteristic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.bot.model.act.actions.Action;
import app.bot.model.act.actions.BaseAction;
import app.bot.service.ActualHeroService;
import app.dnd.dto.CharacterDnd;
import app.dnd.dto.characteristics.Skill;
import app.dnd.service.Location;
import app.dnd.util.ArrayToColumns;

@Component
public class SkillButtonsBuilder {

	@Autowired
	private SkillSingleButtonBuilder skillSingleButtonBuilder;
	@Autowired
	private ActualHeroService actualHeroService;
	@Autowired
	private ArrayToColumns arrayToColumns;
	
	public BaseAction[][] build(Long id) {

		CharacterDnd character = actualHeroService.getById(id).getCharacter();
		
		Skill[] skills = character.getCharacteristics().getSkills();
		String[] buttons = new String[skills.length];
		for(int i = 0; i < skills.length; i++) {
				buttons[i] = skillSingleButtonBuilder.build(skills[i], character);
		}
		BaseAction[] base = new BaseAction[skills.length];
		for(int i = 0; i < skills.length; i++) {
			base[i] = Action.builder()
					.name(skillSingleButtonBuilder.build(skills[i], character))
					.objectDnd(skills[i])
					.location(Location.CHARACTERISTIC)
					.build();
		}

		return arrayToColumns.rebuild(base,2, BaseAction.class);
	}
}
