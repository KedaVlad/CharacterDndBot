package app.dnd.service.talants.stage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.dnd.model.actions.PoolActions;
import app.dnd.model.actions.SingleAction;
import app.dnd.model.telents.spells.Spell;
import app.dnd.service.talants.logic.MagicSoulLogic;
import app.dnd.util.ArrayToColumns;
import app.player.model.Stage;
import app.bot.model.user.ActualHero;

public interface MagicSoulAction {
	
	BaseAction menu(ActualHero hero, Stage stage);
	BaseAction targetSpell(Stage stage);
}

@Component
class MagicSoulActor implements MagicSoulAction {

	@Autowired
	private MagicSoulLogic magicSoulLogic;
	@Autowired
	private MagicSoulButtonBuilder magicSoulButtonBuilder;
	
	
	@Override
	public BaseAction menu(ActualHero hero, Stage stage) {
		return PoolActions.builder()
				.actionsPool(magicSoulButtonBuilder.spell(magicSoulLogic.spellList(hero)))
				.text("Here is your spells.")
				.build();
	}

	@Override
	public BaseAction targetSpell(Stage stage) {
		
		Action action = (Action) stage;
		Spell spell = (Spell) action.getObjectDnd();
		action.setButtons(new String[][] {{"Cast"}});
		action.setText(spell.getName() + "\n" + spell.getDescription());
		return action;
	}
	
}

@Component
class MagicSoulButtonBuilder {

	@Autowired
	private ArrayToColumns arrayToColumns;

	
	public SingleAction[][] spell(List<Spell> list) {

		SingleAction[] pool = new SingleAction[list.size()];
		int i = 0;
		for(Spell spell: list) {
			pool[i] = Action.builder().name(spell.getName()).objectDnd(spell).build();
			i++;
		}
		
		return arrayToColumns.rebuild(pool, 1, SingleAction.class);
	}

}