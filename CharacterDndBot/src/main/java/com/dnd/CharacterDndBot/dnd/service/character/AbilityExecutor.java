package com.dnd.CharacterDndBot.dnd.service.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dnd.CharacterDndBot.bot.model.act.Act;
import com.dnd.CharacterDndBot.bot.model.act.ReturnAct;
import com.dnd.CharacterDndBot.bot.model.act.SingleAct;
import com.dnd.CharacterDndBot.bot.model.act.actions.Action;
import com.dnd.CharacterDndBot.bot.model.act.actions.BaseAction;
import com.dnd.CharacterDndBot.bot.model.act.actions.PoolActions;
import com.dnd.CharacterDndBot.bot.model.user.User;
import com.dnd.CharacterDndBot.dnd.dto.ability.Ability;
import com.dnd.CharacterDndBot.dnd.dto.ability.features.ActiveFeature;
import com.dnd.CharacterDndBot.dnd.dto.ability.features.Feature;
import com.dnd.CharacterDndBot.dnd.dto.ability.proficiency.Possession;
import com.dnd.CharacterDndBot.dnd.dto.ability.spells.MagicSoul;
import com.dnd.CharacterDndBot.dnd.dto.ability.spells.Spell;
import com.dnd.CharacterDndBot.dnd.service.Executor;
import com.dnd.CharacterDndBot.dnd.service.Location;
import com.dnd.CharacterDndBot.dnd.service.logic.proficiencies.HintList;
import com.dnd.CharacterDndBot.dnd.service.logic.proficiencies.ProficienciesAddPossession;
import com.dnd.CharacterDndBot.dnd.service.logic.proficiencies.ProficienciesInformator;

@Service
public class AbilityExecutor implements Executor<Action> {

	@Autowired
	private AbilityMenu abilityMenu;
	@Autowired
	private AbilityTypeMenu abilityTypeMenu;

	@Override
	public Act executeFor(Action action, User user) {

		if (action.condition() == 0 && action.getObjectDnd() == null) {
			return abilityMenu.executeFor(action, user);
		} else {
			return abilityTypeMenu.executeFor(action, user);
		}
	}
}

@Component
class AbilityMenu implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
		{
			String instruction = "Some instruction for ABILITY interface";
			String[][] buttons;	
			if (user.getCharactersPool().getActual().getAbility().getMagicSoul() == null) {
				buttons = new String[][] { { FEATURE_B, POSSESSION_B }, { RETURN_TO_MENU } };
			} else {
				buttons = new String[][] { { FEATURE_B, SPELL_B, POSSESSION_B }, { RETURN_TO_MENU } };
			}

			return ReturnAct.builder()
					.target(MENU_B)
					.act(SingleAct.builder()
							.name(ABILITY_B)
							.text(instruction)
							.action(Action.builder()
									.buttons(buttons)
									.location(Location.ABILITY)
									.replyButtons()
									.build())
							.build())
					.build();
		}
	}
}

@Component
class AbilityTypeMenu implements Executor<Action> {

	@Autowired
	private FeatureExecutor featureExecutor;
	@Autowired
	private SpellExecutor spellExecutor;
	@Autowired
	private PossessionExecutor possessionExecutor;

	@Override
	public Act executeFor(Action action, User user) {
		if (action.getObjectDnd() == null) {
			String target = action.getAnswers()[0];
			if (target.equals(FEATURE_B)) {
				return featureExecutor.executeFor(action, user);
			} else if (target.equals(POSSESSION_B)) {
				return possessionExecutor.executeFor(action, user);
			} else if (target.equals(SPELL_B)) {
				return spellExecutor.executeFor(action, user);
			} else {
				return ReturnAct.builder().target(MENU_B).build();
			}
		} else {
			if (action.getObjectDnd() instanceof Feature) {
				return featureExecutor.executeFor(action, user);
			} else if (action.getObjectDnd() instanceof Spell) {
				return spellExecutor.executeFor(action, user);
			} else {
				return ReturnAct.builder().target(MENU_B).build();
			}
		}
	}
}

@Component
class FeatureExecutor implements Executor<Action> {

	@Autowired
	private FeatureMenu featureMenu;
	@Autowired
	private FeatureTarget featureTarget;

	@Override
	public Act executeFor(Action action, User user) {

		if (action.getObjectDnd() == null) {
			return featureMenu.executeFor(action, user);
		} else {
			return featureTarget.executeFor(action, user);
		}
	}
}

@Component
class FeatureMenu implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {

		Ability ability = user.getCharactersPool().getActual().getAbility();
		BaseAction[][] pool = new BaseAction[ability.getFeatures().size()][1];
		for(int i = 0; i < ability.getFeatures().size(); i++)
		{
			Feature feature = ability.getFeatures().get(i);
			pool[i][0] = Action.builder().name(feature.getName()).location(Location.ABILITY).objectDnd(feature).build();
		}
		return ReturnAct.builder()
				.target(ABILITY_B)
				.act(SingleAct.builder()
						.name(FEATURE_B)
						.text("This is your feature. Choose some for more infotmation")
						.action(PoolActions.builder()
								.actionsPool(pool)
								.build())
						.build())
				.build();
	}
}

@Component
class FeatureTarget implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {

		Feature feature = (Feature) action.getObjectDnd();
		String name = feature.getName();
		String text = name + "\n" + feature.getDescription();
		if (feature instanceof ActiveFeature) {
			action.setButtons(new String[][] {{"Cast"}});
			return SingleAct.builder()
					.name(name)
					.text(text)
					.action(action)
					.build();
		} else {
			return SingleAct.builder()
					.name(name)
					.text(text)
					.build();
		}
	}
}

@Component
class SpellExecutor implements Executor<Action> {

	@Autowired
	private SpellTarget spellTarget;
	@Autowired
	private SpellMenu spellMenu;

	@Override
	public Act executeFor(Action action, User user) {

		if (action.getObjectDnd() == null) {
			return spellMenu.executeFor(action, user);
		} else {
			switch (action.condition()) {
			case 0:
				return spellTarget.executeFor(action, user);
			default:
				return ReturnAct.builder().target(ABILITY_B).build();
			}
		}
	}
}

@Component
class SpellMenu implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {

		MagicSoul magicSoul = user.getCharactersPool().getActual().getAbility().getMagicSoul();

		BaseAction[][] pool = new BaseAction[magicSoul.getPoolCantrips().getActive().size() + magicSoul.getPoolSpells().getActive().size()][1];
		int i = 0;
		for(Spell spell: magicSoul.getPoolCantrips().getActive()) {
			pool[i][0] = Action.builder().name(spell.getName()).objectDnd(spell).build();
			i++;
		}
		for(Spell spell: magicSoul.getPoolSpells().getActive()) {
			pool[i][0] = Action.builder().name(spell.getName()).objectDnd(spell).build();
			i++;
		}
		return ReturnAct.builder()
				.target(ABILITY_B)
				.act(SingleAct.builder()
						.name(FEATURE_B)
						.text("This is your feature. Choose some for more infotmation")
						.action(PoolActions.builder()
								.actionsPool(pool)
								.build())
						.build())
				.build();
	}
}

@Component
class SpellTarget implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {

		Spell spell = (Spell) action.getObjectDnd();
		String name = spell.getName();
		String text = name + "\n" + spell.getDescription();

		action.setButtons(new String[][] {{"Cast"}});
		return SingleAct.builder()
				.name(name)
				.text(text)
				.action(action)
				.build();
	}
}

@Component
class PossessionExecutor implements Executor<Action> {

	@Autowired
	private PossessionMenu possessionMenu;
	@Autowired
	private PossessionCreate possessionCreate;
	@Autowired
	private PossessionAnswerForCall possessionAnswerForCall;
	
	@Override
	public Act executeFor(Action action, User user) {

		switch(action.condition())
		{
		case 1:
			return possessionMenu.executeFor(action, user);
		case 2:
			return possessionCreate.executeFor(action, user);
		case 3:
			return possessionAnswerForCall.executeFor(action, user);
		default:
			return ReturnAct.builder().target(ABILITY_B).build();
		}
	}
}

@Component
class PossessionMenu implements Executor<Action> {

	@Autowired
	private ProficienciesInformator proficienciesInformator;

	@Override
	public Act executeFor(Action action, User user) {
		action.setButtons(new String[][]{{"Add possession"},{RETURN_TO_MENU}});
		action.setReplyButtons(true);
		return ReturnAct.builder()
				.target(ABILITY_B)
				.act(SingleAct.builder()
						.name(POSSESSION_B)
						.text(proficienciesInformator.info(user.getCharactersPool().getActual().getAbility().getProficiencies()))
						.action(action)
						.build())
				.build();
	}
}

@Component
class PossessionCreate implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
		String text = "If you want to add possession of some Skill/Save roll from characteristics - you can do it right by using this pattern (CHARACTERISTIC > SKILLS > Up to proficiency)\n"
				+ "If it`s possession of Weapon/Armor you shood to write ritht the type(correct spelling in Hint list)\n"
				+ "But if it concerns something else(language or metier) write as you like.";
		action.setButtons( new String[][] {{"Hint list"},{"Return to abylity"}});
		action.setMediator(true);
		action.setReplyButtons(true);
		return SingleAct.builder()
				.name("addPossession")
				.text(text)
				.action(action)
				.build();
	}
}

@Component
class PossessionAnswerForCall implements Executor<Action> {

	@Autowired
	private HintList hintList;
	@Autowired
	private ProficienciesAddPossession proficienciesAddPossession;

	@Override
	public Act executeFor(Action action, User user) {

		if(action.getAnswers()[2].equals("Return to abylity")) {
			return ReturnAct.builder().target(MENU_B).call(ABILITY_B).build();
		} else if(action.getAnswers()[2].equals("Hint list")) {
			return SingleAct.builder().name("Hint list").text(hintList.build()).build();
		} else {
			proficienciesAddPossession.add(user.getCharactersPool().getActual().getAbility().getProficiencies(), new Possession(action.getAnswers()[2]));
			return ReturnAct.builder().target(ABILITY_B).call(POSSESSION_B).build();
		}
	}
}
