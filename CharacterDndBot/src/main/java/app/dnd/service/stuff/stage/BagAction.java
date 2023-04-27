package app.dnd.service.stuff.stage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.actions.PoolActions;
import app.dnd.model.actions.SingleAction;
import app.dnd.model.stuffs.ItemInHand;
import app.dnd.model.stuffs.items.Ammunition;
import app.dnd.model.stuffs.items.Armor;
import app.dnd.model.stuffs.items.Items;
import app.dnd.model.stuffs.items.Weapon;
import app.dnd.service.stuff.logic.BagLogic;
import app.dnd.util.ArrayToColumns;
import app.player.model.Stage;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.bot.model.user.ActualHero;

public interface BagAction {
	Stage menu(ActualHero actualHero);

	Stage targetItemMenu(Action action);

	Stage inHandMenu(ActualHero actualHero);

	Stage targetInHandMenu(Action action);

}

@Component
class BagActor implements BagAction{

	@Autowired
	private BagLogic bagLogic;
	@Autowired
	private BagButtonBuilder bagButtonBuilder;

	@Override
	public Stage menu(ActualHero actualHero) {
		return PoolActions.builder()
				.text(bagLogic.bagInfo(actualHero))
				.actionsPool(bagButtonBuilder.bagMenu(bagLogic.getItemsIncideBag(actualHero)))
				.build();
	}

	@Override
	public Stage targetItemMenu(Action action) {

		Items item = (Items) action.getObjectDnd();
		if (item instanceof Armor) {
			action.setButtons(new String[][] { { Button.WEAR.NAME, Button.THROW_OUT.NAME } });
		} else if (item instanceof Weapon) {
			action.setButtons(new String[][] { { Button.PREPEAR.NAME, Button.THROW_OUT.NAME } });
		} else if (item instanceof Ammunition) {
			action.setButtons(new String[][] { { Button.TOP_UP.NAME, Button.THROW_OUT.NAME } });
		} else {
			action.setButtons(new String[][] { { Button.THROW_OUT.NAME } });
		}

		action.setText(item.getDescription());
		return action;
	}

	@Override
	public Stage inHandMenu(ActualHero actualHero) {

		List<ItemInHand> items = bagLogic.getItemsInHand(actualHero);

		if(items.size() > 0) {
			return PoolActions.builder()
					.text("Here, you can access and interact with the items that are you operate. You can easily manipulate and utilize these items to aid you in your adventures. Simply select an item to view its details and available actions.")
					.actionsPool(bagButtonBuilder.inHandMenu(items))
					.build();

		} else { 	
			return Action.builder()
					.text("No prepeared or weared items yeat...\nFor prepearing you shood choose some item in your bag and choose \"PREPEAR\"")
					.build();
		}


	}

	@Override
	public Stage targetInHandMenu(Action action) {
		ItemInHand item = (ItemInHand) action.getObjectDnd();
		if(item.getItem() instanceof Weapon) {
			action.setName(Button.RETURN.NAME);
			action.setAnswers(new String[] {Button.RETURN.NAME});
			SingleAction[][] pool = new SingleAction[][] {{action}, {Action.builder()
				.objectDnd((Weapon)item.getItem())
				.name(Button.ATTACK.NAME)
				.location(Location.ATTACK_MACHINE)
				.build()}};

				return PoolActions.builder()
						.text("Return to bag or attack?")
						.actionsPool(pool)
						.build();
		} else {
			action.setText("Return to bag?");
			action.setButtons(new String[][] {{Button.RETURN.NAME}});
			return action;
		}
	}
}

@Component
class BagButtonBuilder {

	@Autowired
	private ArrayToColumns arrayToColums;

	public SingleAction[][] bagMenu(List<Items> stuffInBag) {
		SingleAction[] buttons = new SingleAction[stuffInBag.size() + 1];

		for (int i = 0; i < buttons.length - 1; i++) {
			buttons[i] = Action.builder()
					.name(stuffInBag.get(i).getName())
					.location(Location.STUFF)
					.objectDnd(stuffInBag.get(i-1))
					.build();
		}

		buttons[stuffInBag.size()] = Action.builder().name(Button.ADD.NAME).location(Location.ITEM_FACTORY).build();
		return arrayToColums.rebuild(buttons, 1, SingleAction.class);
	} 

	public SingleAction[][] inHandMenu(List<ItemInHand> stuffInBag) {
		SingleAction[] buttons = new SingleAction[stuffInBag.size() + 1];

		for (int i = 0; i < buttons.length - 1; i++) {
			buttons[i] = Action.builder()
					.name(stuffInBag.get(i).getItem().getName())
					.location(Location.STUFF)
					.objectDnd(stuffInBag.get(i-1))
					.build();
		}

		buttons[stuffInBag.size()] = Action.builder().name(Button.ADD.NAME).location(Location.ITEM_FACTORY).build();
		return arrayToColums.rebuild(buttons, 1, SingleAction.class);
	} 




}

