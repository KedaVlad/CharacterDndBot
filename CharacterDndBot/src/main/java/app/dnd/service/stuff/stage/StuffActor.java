package app.dnd.service.stuff.stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.service.stuff.logic.StuffLogic;
import app.player.model.Stage;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.user.model.ActualHero;

@Component
public class StuffActor implements StuffAction {

	@Autowired
	private StuffLogic stuffLogic;
	@Autowired
	private WalletAction walletAction;
	@Autowired
	private BagAction bagAction;

	@Override
	public Stage menu(ActualHero actualHero) {
		return Action.builder()
				.location(Location.STUFF)
				.text(stuffLogic.stuffMenuInfo(actualHero))
				.buttons(new String[][] {{Button.CARRYING_STUFF.NAME, Button.BAG.NAME, Button.WALLET.NAME},{Button.RETURN_TO_MENU.NAME}})
				.build();
	}

	@Override
	public WalletAction wallet() {
		return walletAction;
	}

	@Override
	public BagAction bag() {
		return bagAction;
	}

	@Override
	public Stage factoryMenu() {

		return Action.builder()
				.text("What item you take?")
				.location(Location.ITEM_FACTORY)
				.buttons(new String[][]
						{{Button.WEAPON.NAME, Button.AMNUNITION.NAME},
					{Button.TOOL.NAME, Button.PACK.NAME},
					{Button.ARMOR.NAME},
					{Button.ELSE.NAME}})
				.build();
	}


}
