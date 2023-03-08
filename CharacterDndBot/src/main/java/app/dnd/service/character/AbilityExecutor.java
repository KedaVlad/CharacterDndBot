package app.dnd.service.character;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.bot.model.act.Act;
import app.bot.model.act.ReturnAct;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.act.actions.BaseAction;
import app.bot.model.act.actions.PoolActions;
import app.bot.model.user.User;
import app.dnd.dto.ability.Ability;
import app.dnd.dto.ability.features.ActiveFeature;
import app.dnd.dto.ability.features.Feature;
import app.dnd.dto.ability.proficiency.Possession;
import app.dnd.dto.ability.spells.MagicSoul;
import app.dnd.dto.ability.spells.Spell;
import app.dnd.service.Executor;
import app.dnd.service.Location;
import app.dnd.service.logic.proficiencies.HintList;
import app.dnd.service.logic.proficiencies.ProficienciesAddPossession;
import app.dnd.service.logic.proficiencies.ProficienciesInformator;
import app.dnd.util.ArrayToColumns;

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

		Ability ability = user.getActualHero().getCharacter().getAbility();
		String instruction = "Here, you can access and manage your character's ta talents, including " + TRAIT_B + ", " + FEATURE_B + ", " + FEAT_B + ", " + POSSESSION_B + " and " + SPELL_B;
		String[][] buttons;	

		if (ability.getMagicSoul() == null && ability.getFeats().size() == 0) {
			buttons = new String[][] {{TRAIT_B, FEATURE_B, POSSESSION_B }, { RETURN_TO_MENU }};
		} else if(ability.getMagicSoul() == null) {
			buttons = new String[][] {{ FEATURE_B, POSSESSION_B }, {TRAIT_B, FEAT_B}, { RETURN_TO_MENU }};
		} else if(ability.getFeats().size() == 0) {
			buttons = new String[][] {{ TRAIT_B, POSSESSION_B }, {FEATURE_B, SPELL_B}, { RETURN_TO_MENU }};
		} else {
			buttons = new String[][] {{ TRAIT_B, FEATURE_B, FEAT_B}, { POSSESSION_B, SPELL_B}, { RETURN_TO_MENU }};
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

@Component
class AbilityTypeMenu implements Executor<Action> {

	@Autowired
	private FeatureExecutor featureExecutor;
	@Autowired
	private SpellExecutor spellExecutor;
	@Autowired
	private PossessionExecutor possessionExecutor;
	@Autowired
	private FeatureTarget featureTarget;

	@Override
	public Act executeFor(Action action, User user) {
		if (action.getObjectDnd() == null) {
			String target = action.getAnswers()[0];
			if (target.equals(POSSESSION_B)) {
				return possessionExecutor.executeFor(action, user);
			} else if (target.equals(SPELL_B)) {
				return spellExecutor.executeFor(action, user);
			} else {
				return featureExecutor.executeFor(action, user);
			}
		} else {
			if (action.getObjectDnd() instanceof Feature) {
				return featureTarget.executeFor(action, user);
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
	private ArrayToColumns arrayToColumns;

	@Override
	public Act executeFor(Action action, User user) {

		Ability ability = user.getActualHero().getCharacter().getAbility();
		String text = "ERROR";
		BaseAction[][] pool = null;
		switch(action.getAnswers()[0]) {
		case FEATURE_B:
			text = "Here, you can find the features. Talents that are bestowed upon you by your class.";
			pool = featuresToColumns(ability.getFeatures());
			break;
		case FEAT_B:
			text = "Here, you can find the feats.";
			pool = featuresToColumns(ability.getFeats());
			break;
		case TRAIT_B:
			text = "Here, you can find the traits. Talents that are bestowed upon you by your race.";
			pool = featuresToColumns(ability.getTraits());
			break;
		}

		return ReturnAct.builder()
				.target(ABILITY_B)
				.act(SingleAct.builder()
						.name(FEATURE_B)
						.text(text)
						.action(PoolActions.builder()
								.actionsPool(pool)
								.build())
						.build())
				.build();
	}

	private BaseAction[][] featuresToColumns(List<Feature> list) {
		BaseAction[] all = new BaseAction[list.size()];
		for(int i = 0; i < list.size(); i++) {
			all[i] = Action.builder()
					.name(list.get(i).getName())
					.objectDnd(list.get(i))
					.location(Location.ABILITY)
					.build();
		}
		return arrayToColumns.rebuild(all, 1, BaseAction.class);
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

		MagicSoul magicSoul = user.getActualHero().getCharacter().getAbility().getMagicSoul();

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
						.text("This is your spells. Choose some for more infotmation")
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
						.text(proficienciesInformator.info(user.getActualHero().getCharacter().getAbility().getProficiencies()))
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
			proficienciesAddPossession.add(user.getActualHero().getCharacter(), new Possession(action.getAnswers()[2]));
			return ReturnAct.builder().target(ABILITY_B).call(POSSESSION_B).build();
		}
	}
}
