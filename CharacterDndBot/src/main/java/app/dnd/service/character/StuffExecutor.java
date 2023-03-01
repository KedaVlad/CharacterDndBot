package app.dnd.service.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.bot.model.ActualHero;
import app.bot.model.act.Act;
import app.bot.model.act.ReturnAct;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.act.actions.BaseAction;
import app.bot.model.act.actions.PoolActions;
import app.bot.model.user.User;
import app.bot.service.ActualHeroService;
import app.dnd.dto.stuffs.Stuff;
import app.dnd.dto.stuffs.Wallet;
import app.dnd.dto.stuffs.items.Ammunition;
import app.dnd.dto.stuffs.items.Armor;
import app.dnd.dto.stuffs.items.Items;
import app.dnd.dto.stuffs.items.Weapon;
import app.dnd.service.ButtonName;
import app.dnd.service.Executor;
import app.dnd.service.Location;
import app.dnd.service.factory.InformatorHandler;
import app.dnd.service.logic.stuff.bag.AmmunitionTopUp;
import app.dnd.service.logic.stuff.bag.CarringRemove;
import app.dnd.service.logic.stuff.bag.ItemCarry;
import app.dnd.service.logic.stuff.wallet.WalletAddCoin;
import app.dnd.service.logic.stuff.wallet.WalletLostCoin;

@Service
public class StuffExecutor implements Executor<Action> {

	@Autowired
	private StuffMenu stuffMenu;
	@Autowired
	private WalletExecutor walletExecutor;
	@Autowired
	private BagExecutor bagExecutor;
	@Autowired
	private CarryingExecutor carryingExecutor;

	@Override
	public Act executeFor(Action action, User user) {

		if (action.getObjectDnd() == null) {
			if (action.condition() == 0) {
				return stuffMenu.executeFor(action, user);
			} else {
				String targetMenu = action.getAnswers()[0];
				if (targetMenu.equals(CARRYING_STUFF_B)) {
					return carryingExecutor.executeFor(action, user);
				} else if (targetMenu.equals(BAG_B)) {
					return bagExecutor.executeFor(action, user);
				} else if (targetMenu.equals(WALLET_B)) {
					return walletExecutor.executeFor(action, user);
				} else {
					return ReturnAct.builder().target(MENU_B).build();
				}
			}
		} else {
			if (action.getObjectDnd() instanceof Items) {
				Items item = (Items) action.getObjectDnd();
				if (item.isUsed()) {
					return carryingExecutor.executeFor(action, user);
				} else {
					return bagExecutor.executeFor(action, user);
				}
			} else { 
				return ReturnAct.builder().target(MENU_B).build();
			}
		}
	}
}

@Component	
class StuffMenu implements Executor<Action> {

	@Autowired
	private InformatorHandler informatorHandler;
	@Autowired
	private ActualHeroService actualHeroService;

	@Override
	public Act executeFor(Action action, User user) {
		return ReturnAct.builder()
				.target(MENU_B)
				.act(SingleAct.builder()
						.name(STUFF_B)
						.text(informatorHandler.handle(actualHeroService.getById(user.getId()).getCharacter().getStuff()))
						.action(Action.builder()
								.location(Location.STUFF)
								.buttons(new String[][] {{CARRYING_STUFF_B, BAG_B, WALLET_B},{RETURN_TO_MENU}})
								.replyButtons()
								.build())
						.build())
				.build();
	}

}

@Service
class WalletExecutor implements Executor<Action> {

	@Autowired
	private WalletMenu walletMenu;
	@Autowired
	private WalletChangeCurrency walletChangeCurrency;
	@Autowired
	private WalletCompleatCurrencyChange walletCompleatCurrencyChange;

	@Override
	public Act executeFor(Action action, User user) {

		switch(action.condition()) {
		case 1:
			return walletMenu.executeFor(action, user);
		case 2:
			return walletChangeCurrency.executeFor(action, user);
		case 3:
			return walletCompleatCurrencyChange.executeFor(action, user);
		default:
			return ReturnAct.builder().target(STUFF_B).call(STUFF_B).build();
		}
	}
}

@Component
class WalletMenu implements Executor<Action> {

	@Autowired
	private ActualHeroService actualHeroService;

	@Override
	public Act executeFor(Action action, User user) {

		action.setButtons(new String[][] {{"CP", "SP", "GP", "PP"}});
		return SingleAct.builder()
				.name(WALLET_B)
				.text(actualHeroService.getById(user.getId()).getCharacter().getStuff().getWallet().toString() + "\n Choose currency...")
				.action(action)
				.build();
	}
}

@Component
class WalletChangeCurrency implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {

		action.setMediator(true);
		return SingleAct.builder()
				.name("changeWallet")
				.text("Earned(+) or spent(-)?(Write)")
				.action(action)
				.build();
	}
}

@Component
class WalletCompleatCurrencyChange implements Executor<Action> {

	@Autowired
	private WalletAddCoin walletAddCoin;
	@Autowired
	private WalletLostCoin walletLostCoin;
	@Autowired
	private ActualHeroService actualHeroService;

	@Override
	public Act executeFor(Action action, User user) {

		ActualHero actualHero = actualHeroService.getById(user.getId());
		Wallet wallet = actualHero.getCharacter().getStuff().getWallet();
		if (action.getAnswers()[2].matches("\\+(\\d{1,6})")) {
			walletAddCoin.add(wallet, action.getAnswers()[1], Integer.parseInt(action.getAnswers()[2].replaceAll("\\+(\\d{1,6})", "$1")));
			actualHeroService.save(actualHero);
			return ReturnAct.builder().target(STUFF_B).call(WALLET_B).build();
		} else if (action.getAnswers()[2].matches("-(\\d+)")) {
			if (walletLostCoin.lost(wallet, action.getAnswers()[1], Integer.parseInt(action.getAnswers()[2].replaceAll("-(\\d{1,6})", "$1")))) {
				actualHeroService.save(actualHero);
				return ReturnAct.builder().target(STUFF_B).call(WALLET_B).build();
			} else {
				return SingleAct.builder().name("DeadEnd").text("You don`t have enough coins for that ;(").build();
			}
		} else {
			return SingleAct.builder().name("DeadEnd").text("So earned(+) or spent(-)? Examples : +10, -10").build();
		}
	}
}

@Service
class BagExecutor implements Executor<Action> {

	@Autowired
	private BagMenu bagMenu;
	@Autowired
	private ItemTypeMenu itemTypeMenu;
	@Autowired
	private CompleatItemTask compleatItemTask;

	@Override
	public Act executeFor(Action action, User user) {

		if (action.getObjectDnd() == null) {
			return bagMenu.executeFor(action, user);
		} else if (action.getAnswers() == null) {
			return itemTypeMenu.executeFor(action, user);
		} else {
			return compleatItemTask.executeFor(action, user);
		}
	}
}

@Component
class BagMenu implements Executor<Action> {

	@Autowired
	private ActualHeroService actualHeroService;

	@Override
	public Act executeFor(Action action, User user) {
		BaseAction[][] buttons;
		String text;
		Stuff stuff = actualHeroService.getById(user.getId()).getCharacter().getStuff();
		if (stuff.getInsideBag().size() == 0) {
			text = "Warning: Your "+ ButtonName.BAG_B +" is currently empty. You may want to consider adding an item to assist you on your journey. Would you like to "+ButtonName.ADD_B+" an item now?";
			buttons = new BaseAction[][] {{ Action.builder().name("ADD ITEM").location(Location.ITEM_FACTORY).build() }};
		} else {
			text = BAG_B +  "\r\n"
					+ "\r\n" 
					+ ADD_B +": Add the element to the backpack\r\n"		
					+ THROW_OUT +": delete the element from the backpack\r\n"
					+ USE_B+"Use the element: Use the item that can be used by one -time (potion, scroll)\r\n"
					+ PREPEAR + "|" + WEAR +": Equip stuff from backpack, such as armor or weapon\r\n"
					+ "\r\n"
					+ "Please note that the availability of certain interactions may depend on the element and its properties. In addition, remember, some objects cannot be transferred to a backpack from their size.";
			buttons = new BaseAction[stuff.getInsideBag().size() + 1][];
			buttons[0] = new BaseAction[] {
					Action.builder().name(ADD_B).location(Location.ITEM_FACTORY).build() };
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
}

@Component
class ItemTypeMenu implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {

		Items item = (Items) action.getObjectDnd();
		String[][] buttons;
		if (item instanceof Armor) {
			buttons = new String[][] { { WEAR, THROW_OUT } };
		} else if (item instanceof Weapon) {
			buttons = new String[][] { { PREPEAR, THROW_OUT } };
		} else if (item instanceof Ammunition) {
			buttons = new String[][] { { TOP_UP, THROW_OUT } };
		} else {
			buttons = new String[][] { { THROW_OUT } };
		}
		action.setButtons(buttons);
		return SingleAct.builder().name(item.getName()).text(item.getDescription()).action(action).build();
	}
}

@Component
class CompleatItemTask implements Executor<Action> {

	@Autowired
	private ItemCarry itemCarry;
	@Autowired
	private AmmunitionTopUp ammunitionTopUp;
	@Autowired
	private ActualHeroService actualHeroService;

	@Override
	public Act executeFor(Action action, User user) {

		Items target = (Items) action.getObjectDnd();
		String answer = action.getAnswers()[0];
		if (answer.contains(THROW_OUT)) {
			ActualHero actualHero = actualHeroService.getById(user.getId());
			actualHero.getCharacter().getStuff().getInsideBag().remove((Items) target);
			actualHeroService.save(actualHero);
		} else if (answer.equals(WEAR) || answer.equals(PREPEAR)) {
			itemCarry.carry(user.getId(), target);
		} else if (answer.equals(TOP_UP)) {
			if (action.condition() > 1) {
				ammunitionTopUp.topUp(user.getId(), (Ammunition) target, action.getAnswers()[1]);
			} else {
				action.setMediator(true);
				return SingleAct.builder().name(TOP_UP).text("How many?(Write)").action(action).build();
			}
		}
		return ReturnAct.builder().target(STUFF_B).call(BAG_B).build();
	}
}


@Service
class CarryingExecutor implements Executor<Action> {

	@Autowired
	private CarryingMenu carryingMenu;
	@Autowired
	private CarryingTypeMenu carryingTypeMenu;
	@Autowired
	private CompleatCarringTask compleatCarringTask;

	@Override
	public Act executeFor(Action action, User user) {
		if (action.getObjectDnd() == null) {
			return carryingMenu.executeFor(action, user);
		} else if (action.getAnswers() == null) {
			return carryingTypeMenu.executeFor(action, user);
		} else {
			return compleatCarringTask.executeFor(action, user);
		}
	}
}

@Service
class CarryingMenu implements Executor<Action> {

	@Autowired
	private ActualHeroService actualHeroService;

	@Override
	public Act executeFor(Action action, User user) {
		{
			Stuff stuff = actualHeroService.getById(user.getId()).getCharacter().getStuff();
			if(stuff.getPrepeared().size() > 0)
			{
				BaseAction[][] buttons = new BaseAction[stuff.getPrepeared().size()][1];
				for(int i = 0; i < stuff.getPrepeared().size(); i++)
				{
					buttons[i][0] = Action.builder()
							.name(stuff.getPrepeared().get(i).getName())
							.objectDnd(stuff.getPrepeared().get(i))
							.location(Location.STUFF)
							.build();
				}
				return SingleAct.builder()
						.name(CARRYING_STUFF_B)
						.text("Here, you can access and interact with the items that are you operate. You can easily manipulate and utilize these items to aid you in your adventures. Simply select an item to view its details and available actions.")
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
	}
}

@Service
class CarryingTypeMenu implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {

		Items item = (Items) action.getObjectDnd();
		if(item instanceof Weapon) {
			action.setName(RETURN);
			action.setAnswers(new String[] {RETURN});
			BaseAction[][] pool = new BaseAction[][] {{action}, {Action.builder()
						.objectDnd(item)
						.name(ATTACK)
						.location(Location.ATTACK_MACHINE)
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
}

@Component
class CompleatCarringTask implements Executor<Action> {

	@Autowired
	private CarringRemove carringRemove;

	@Override
	public Act executeFor(Action action, User user) {

		String answer = action.getAnswers()[0];
		Items item = (Items)action.getObjectDnd();
		if (answer.contains(RETURN)) {
			carringRemove.remove(user.getId(), item);
		}
		return ReturnAct.builder().target(STUFF_B).call(CARRYING_STUFF_B).build();
	}
}

