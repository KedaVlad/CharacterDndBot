package com.dnd.CharacterDndBot.service.dndTable.dndService.characterService;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.acts.actions.BaseAction;
import com.dnd.CharacterDndBot.service.acts.actions.PoolActions;
import com.dnd.CharacterDndBot.service.bot.user.User;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.Stuff;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.Wallet;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Ammunition;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Armor;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Items;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Weapon;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Location;

public class StuffMenu implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
		
		if (action.getObjectDnd() == null) {
			if (action.condition() == 0) {
				return stuff(user);
			} else {
		/*		String targetMenu = action.getAnswers()[0];
				if (targetMenu.equals(CARRYING_STUFF_B)) {
					return carrying.execute(action);
				} else if (targetMenu.equals(BAG_B)) {
					return bag.execute(action);
				} else if (targetMenu.equals(WALLET_B)) {
					return new WalletMenu(action).executeFor(user);
				} else {
					return SingleAct.builder().returnTo(MENU_B, MENU_B).build();
				}
			}
		} else {
			if (action.getObjectDnd() instanceof Items) {
				Items item = (Items) action.getObjectDnd();
				if (item.isUsed()) {
					return carrying.execute(action);
				} else {
					return bag.execute(action);
				}
			} else { */
				return null; //SingleAct.builder().returnTo(MENU_B, MENU_B).build();
			}

		}
		return null; 
	}

	private Act stuff(User user) {
		return ReturnAct.builder()
				.target(MENU_B)
				.act(SingleAct.builder()
						.name(STUFF_B)
						.text(InformatorFactory.build(user.getCharactersPool().getActual().getStuff()).getInformation())
						.action(Action.builder()
								.location(Location.STUFF)
								.buttons(new String[][] {{CARRYING_STUFF_B, BAG_B, WALLET_B},{RETURN_TO_MENU}})
								.replyButtons()
								.build())
						.build())
				.build();
	}

}

class WalletMenu extends Executor<Action> {

	private Wallet wallet;
	public WalletMenu(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		wallet = user.getCharactersPool().getActual().getStuff().getWallet();
		switch(action.condition()) {
		case 1:
			return walletMenu();
		case 2:
			return changeWallet();
		case 3:
			return changeCarrencyValue();
		default:
			return ReturnAct.builder().target(STUFF_B).call(STUFF_B).build();
		}
	}

	private Act walletMenu() {
		action.setButtons(new String[][] {{"CP", "SP", "GP", "PP"}});
		return SingleAct.builder()
				.name(WALLET_B)
				.text(wallet.toString() + "\n Choose currency...")
				.action(action)
				.build();
	}

	private Act changeWallet() {
		action.setMediator(true);
		return SingleAct.builder()
				.name("changeWallet")
				.text("Earned(+) or spent(-)?(Write)")
				.action(action)
				.build();
	}

	private Act changeCarrencyValue() {
		if(action.getAnswers()[2].matches("\\+(\\d+)")) {
			wallet.addCoin(action.getAnswers()[1], Integer.parseInt(action.getAnswers()[2].replaceAll("\\+(\\d+)", "$1")));
			return ReturnAct.builder().target(STUFF_B).call(WALLET_B).build();
		} else if(action.getAnswers()[2].matches("-(\\d+)")) {
			if(wallet.lostCoin(action.getAnswers()[1], Integer.parseInt(action.getAnswers()[2].replaceAll("-(\\d+)", "$1")))) {
				return ReturnAct.builder().target(STUFF_B).call(WALLET_B).build();
			} else {
				return SingleAct.builder().name("DeadEnd").text("You don`t have enough coins for that ;(").build();
			}
		} else {
			return SingleAct.builder().name("DeadEnd").text("So earned(+) or spent(-)? Examples : +10, -10").build();
		}
	}
	
}
/*
class BagMenu extends Executor<Action> {

	private static final String WEAR = "WEAR";
	private static final String THROW_OUT = "THROW OUT";
	private static final String TOP_UP = "TOP UP";
	private static final String PREPEAR = "PREPEAR";
	private Stuff stuff;
	
	
	public BagMenu(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		stuff = user.getCharactersPool().getActual().getStuff();
		if (action.getObjectDnd() == null) {
			return menu();
		} else if (action.getAnswers() == null) {
			return targetMenu(action);
		} else {
			return change(action);
		}
	}

	private Act menu() {
		BaseAction[][] buttons;
		String text;
		if (stuff.getInsideBag().size() == 0) {
			text = "Yor bag is empty";
			buttons = new BaseAction[][] {{ Action.builder().name("ADD ITEM").location(Location.ITEM_FACTORY).build() }};
		} else {
			text = "Choose item";
			buttons = new BaseAction[stuff.getInsideBag().size() + 1][];
			buttons[0] = new BaseAction[] {
					Action.builder().name("ADD ITEM").location(Location.ITEM_FACTORY).build() };
			for (int i = 1; i < buttons.length; i++) {
				buttons[i] = new BaseAction[] {Action.builder()
						.name(stuff.getInsideBag().get(i-1).getName())
						.location(Location.STUFF)
						.objectDnd(stuff.getInsideBag().get(i-1))
						.build()};
			}
		}
		return SingleAct.builder()
				.name(BAG_B)
				.text(text)
				.action(PoolActions.builder()
						.actionsPool(buttons)
						.build())
				.build();
	}

	private Act targetMenu(Action action) 
	{
		Items item = (Items) action.getObjectDnd();
		if(item instanceof Armor)
		{
			return armorMenu(action);
		}
		else if(item instanceof Weapon)
		{
			return weaponMenu(action);
		}
		else if(item instanceof Ammunition)
		{
			return ammunitionMenu(action);
		}
		else
		{
			return itemMenu(action);
		}
	}

	private Act change(Action action)
	{
		Items target = (Items) action.getObjectDnd();
		String answer = action.getAnswers()[0];
		if(answer.contains(THROW_OUT))
		{
			stuff.getInsideBag().remove((Items)target);
		}
		else if(answer.equals(WEAR))
		{
			stuff.getInsideBag().remove(target);
			target.setUsed(true);
			stuff.getCarryingStuff().getPrepeared().add((Armor) target);
		}
		else if(answer.equals(PREPEAR))
		{
			Weapon weapon = (Weapon) target;
			stuff.getInsideBag().remove(weapon);
			target.setUsed(true);
			stuff.getCarryingStuff().getPrepeared().add(weapon);
		}
		else if(answer.equals(TOP_UP))
		{
			if(action.getAnswers().length > 1)
			{
				Ammunition ammunition = (Ammunition) target;
				String valueInString = action.getAnswers()[1];
				int value = 0;
				Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
				Matcher matcher = pat.matcher(valueInString);
				while (matcher.find()) 
				{
					value = ((Integer) Integer.parseInt(matcher.group()));
				}
				if(valueInString.contains("-")) value = value *-1;
				ammunition.addValue(value);
			}
			else
			{
				action.setMediator(true);
				return SingleAct.builder().name(TOP_UP).text("How many?(Write)").action(action).build();
			}

		}
		return ReturnAct.builder().target(STUFF_B).call(BAG_B).build();
	}


	private Act itemMenu(Action action)
	{
		Items item = (Items) action.getObjectDnd();
		action.setButtons(new String[][] {{THROW_OUT}});
		return SingleAct.builder()
				.name(item.getName())
				.text(item.getDescription())
				.action(action)
				.build();
	}

	private Act armorMenu(Action action)
	{
		Armor armor = (Armor) action.getObjectDnd();
		action.setButtons(new String[][] {{WEAR, THROW_OUT}});
		return SingleAct.builder()
				.name(armor.getName())
				.text(armor.getDescription())
				.action(action)
				.build();
	}

	private Act ammunitionMenu(Action action)
	{
		Ammunition ammunition = (Ammunition) action.getObjectDnd();
		action.setButtons(new String[][] {{TOP_UP, THROW_OUT}});
		return SingleAct.builder()
				.name(ammunition.getName())
				.text(ammunition.getDescription())
				.action(action)
				.build();
	}

	private Act weaponMenu(Action action)
	{
		Weapon weapon = (Weapon) action.getObjectDnd();
		action.setButtons(new String[][] {{PREPEAR, THROW_OUT}});
		return SingleAct.builder()
				.name(weapon.getName())
				.text(weapon.getDescription())
				.action(action)
				.build();
	}
}

class CarryingMenu extends Executor<Action> {

	public CarryingMenu(Action action) {
		super(action);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Act executeFor(User user) {
		if(action.getObjectDnd() == null)
		{
			return menu();
		}
		else if(action.getAnswers() == null)
		{
			return targetMenu(action);			
		}
		else
		{
			return change(action);
		}
	}
	
	private SingleAct menu()
	{
		if(prepeared.size() > 0)
		{
			BaseAction[][] buttons = new BaseAction[prepeared.size()][1];
			for(int i = 0; i < prepeared.size(); i++)
			{
				buttons[i][0] = Action.builder()
						.name(prepeared.get(i).getName())
						.objectDnd(prepeared.get(i))
						.location(Location.CHARACTER)
						.key(key())
						.build();
			}
			return SingleAct.builder()
					.name(CARRYING_STUFF_B)
					.text("Items in quick access (dressed armor, sword in scabbard, potion on belt, and so on...).")
					.action(PoolActions.builder()
							.actionsPool(buttons)
							.build())
					.build();
		}
		return SingleAct.builder()
				.name(CARRYING_STUFF_B)
				.text("No prepeared or weared items yeat...\nFor prepearing you shood choose some item in your bag and choose \"PREPEAR\"")
				.build();
	}
	

	private SingleAct targetMenu(Action action) 
	{
		Items item = (Items) action.getObjectDnd();
		if(item instanceof Weapon)
		{
			action.setName(RETURN);
			action.setAnswers(new String[] {RETURN});
			BaseAction[][] pool = new BaseAction[][]
					{{action},
					{Action.builder()
						.objectDnd(item)
						.name(ATTACK)
						.location(Location.CHARACTER)
						.key(ATTACK_MACHINE)
						.build()}};
			
			return SingleAct.builder()
					.name("targetMenu")
					.text("Return to bag or attack?")
					.action(PoolActions.builder()
							.actionsPool(pool)
							.build())
					.build();
		}
		action.setButtons(new String[][] {{RETURN}});
		return SingleAct.builder()
				.name("targetMenu")
				.text("Return to bag?")
				.action(action)
				.build();
	}

	private SingleAct change(Action action) 
	{
		String answer = action.getAnswers()[0];
		Items item = (Items)action.getObjectDnd();
		if(answer.contains(RETURN))
		{
			prepeared.remove(item);
			bag.add(item);
		}
		return SingleAct.builder().returnTo(STUFF_B, CARRYING_STUFF_B).build();
	}
	
}
*/