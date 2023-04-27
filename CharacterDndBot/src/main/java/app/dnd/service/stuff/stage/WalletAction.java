package app.dnd.service.stuff.stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.service.stuff.logic.WalletLogic;
import app.player.model.Stage;
import app.bot.model.user.ActualHero;

public interface WalletAction {

	Stage menu(ActualHero actualHero, Action action);
	
}

@Component
class WalletActor implements WalletAction {

	@Autowired
	private WalletLogic walletLogic;
	
	@Override
	public Stage menu(ActualHero actualHero, Action action) {
		action.setButtons(new String[][] {{"CP", "SP", "GP", "PP"}});
		action.setText(walletLogic.menuInfo(actualHero) + "\n Choose currency...");
		return action;
	}
	
	
}
