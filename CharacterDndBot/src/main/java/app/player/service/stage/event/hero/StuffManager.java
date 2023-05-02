package app.player.service.stage.event.hero;

import app.player.model.event.StageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.stuffs.ItemInHand;
import app.dnd.model.stuffs.items.Ammunition;
import app.dnd.model.stuffs.items.Armor;
import app.dnd.model.stuffs.items.Items;
import app.dnd.service.DndFacade;
import app.player.model.EventExecutor;
import app.player.model.act.Act;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.player.service.stage.Executor;
import app.bot.model.user.ActualHero;

@EventExecutor(Location.STUFF)
public class StuffManager implements Executor {

	@Autowired
	private StuffExecutor stuffExecutor;

	@Override
	public Act execute(StageEvent event)  {

		Action action = (Action) event.getTusk();
		if (action.getObjectDnd() == null && action.condition() == 0) {
			return stuffExecutor.menu(event.getUser().getActualHero());

		} else if (action.getObjectDnd() == null) {

			String targetMenu = action.getAnswers()[0];
			if (targetMenu.equals(Button.CARRYING_STUFF.NAME)) {
				return stuffExecutor.inHandMenu(event.getUser().getActualHero());

			} else if (targetMenu.equals(Button.BAG.NAME)) {
				return stuffExecutor.bagMenu(event.getUser().getActualHero());

			} else if (targetMenu.equals(Button.WALLET.NAME)) {
				return switch (action.condition()) {
					case 1 -> stuffExecutor.walletMenu(event.getUser().getActualHero(), action);
					case 2 -> stuffExecutor.carrencyMenu(action);
					case 3 -> stuffExecutor.compleatCurrencyChange(event.getUser().getActualHero(), action);
					default -> ReturnAct.builder().target(Button.STUFF.NAME).build();
				};

			} else {
				return ReturnAct.builder().target(Button.MENU.NAME).build();
			}
		} else if (action.getObjectDnd() instanceof Items) {
			if(action.condition() == 0) {
				return stuffExecutor.itemTypeMenu(action);
			} else {
				return stuffExecutor.compleatItemTask(event.getUser().getActualHero(), action);
			}
		} else if (action.getObjectDnd() instanceof ItemInHand) {
			if(action.condition() == 0) {
				return stuffExecutor.itemInHandTypeMenu(action);
			} else {
				return stuffExecutor.compleatInHandItemTask(event.getUser().getActualHero(), action);
			}
		} else { 
			return ReturnAct.builder().target(Button.MENU.NAME).build();
		}
	}
}

@Component
class StuffExecutor {

	@Autowired
	private DndFacade dndFacade;

	public Act menu(ActualHero actualHero) {
		return ReturnAct.builder()
				.target(Button.MENU.NAME)
				.act(SingleAct.builder()
						.name(Button.STUFF.NAME)
						.reply(true)
						.stage(dndFacade.action().stuff().menu(actualHero))
						.build())
				.build();
	}


	public Act inHandMenu(ActualHero actualHero) {
		return SingleAct.builder()
				.name(Button.BAG.NAME)
				.stage(dndFacade.action().stuff().bag().inHandMenu(actualHero))
				.build();
	}

	public Act itemInHandTypeMenu(Action action) {

		return SingleAct.builder()
				.name("itemInHandTypeMenu")
				.stage(dndFacade.action().stuff().bag().targetInHandMenu(action))
				.build();

	}	

	public Act compleatInHandItemTask(ActualHero actualHero, Action action) {

		if (action.getAnswers()[0].contains(Button.RETURN.NAME)) {
			dndFacade.hero().stuff().bag().fromHandToBag(actualHero, (ItemInHand) action.getObjectDnd());
		}
		return ReturnAct.builder()
				.target(Button.STUFF.NAME)
				.call(Button.CARRYING_STUFF.NAME)
				.build();
	}


	public Act bagMenu(ActualHero actualHero) {
		return SingleAct.builder()
				.name(Button.BAG.NAME)
				.stage(dndFacade.action().stuff().bag().menu(actualHero))
				.build();
	}

	public Act itemTypeMenu(Action action) {
		return SingleAct.builder()
				.name("itemTypeMenu")
				.stage(dndFacade.action().stuff().bag().targetItemMenu(action))
				.build();
	}

	public Act compleatItemTask(ActualHero actualHero, Action action) {

		Items target = (Items) action.getObjectDnd();
		String answer = action.getAnswers()[0];
		if (answer.contains(Button.THROW_OUT.NAME)) {
			dndFacade.hero().stuff().bag().throwItem(actualHero, target);
		} else if (answer.equals(Button.WEAR.NAME)) {
			dndFacade.hero().stuff().bag().wear(actualHero, (Armor) target);
		} else if (answer.equals(Button.PREPEAR.NAME)) {
			dndFacade.hero().stuff().bag().prepear(actualHero, target);
		} else if (answer.equals(Button.TOP_UP.NAME)) {
			if (action.condition() > 1) {
				dndFacade.hero().stuff().bag().topUpAmmunition(actualHero, (Ammunition) target, action.getAnswers()[1]);
			} else {
				action.setText("How many?(Write)");
				return SingleAct.builder()
						.name(Button.TOP_UP.NAME)
						.stage(action)
						.build();
			}
		}
		return ReturnAct.builder().target(Button.STUFF.NAME).call(Button.BAG.NAME).build();
	}


	public Act walletMenu(ActualHero actualHero, Action action) {
		return SingleAct.builder()
				.name(Button.WALLET.NAME)
				.stage(dndFacade.action().stuff().wallet().menu(actualHero, action))
				.build();
	}

	public Act carrencyMenu(Action action) {
		action.setText("Earned(+) or spent(-)? (Write)");
		return SingleAct.builder()
				.name("changeWallet")
				.mediator(true)
				.stage(action)
				.build();
	}

	public Act compleatCurrencyChange(ActualHero actualHero, Action action) {

		if (action.getAnswers()[2].matches("\\+(\\d{1,6})")) {
			dndFacade.hero().stuff().wallet().addCoin(actualHero, action.getAnswers()[1], Integer.parseInt(action.getAnswers()[2].replaceAll("\\+(\\d{1,6})", "$1")));
			return ReturnAct.builder().target(Button.STUFF.NAME).call(Button.WALLET.NAME).build();
		} else if (action.getAnswers()[2].matches("-(\\d+)")) {
			if (dndFacade.hero().stuff().wallet().lostCoin(actualHero, action.getAnswers()[1], Integer.parseInt(action.getAnswers()[2].replaceAll("-(\\d{1,6})", "$1")))) {
				return ReturnAct.builder().target(Button.STUFF.NAME).call(Button.WALLET.NAME).build();
			} else {
				return SingleAct.builder().name("DeadEnd").stage(Action.builder().text("You don`t have enough coins for that ;(").build()).build();
			}
		} else {
			return SingleAct.builder().name("DeadEnd").stage(Action.builder().text("So earned(+) or spent(-)? Examples : +10, -10").build()).build();
		}
	}

}




