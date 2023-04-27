package app.dnd.service.stuff.stage;

import app.player.model.Stage;
import app.bot.model.user.ActualHero;

public interface StuffAction {

	Stage menu(ActualHero actualHero);

	WalletAction wallet();

	BagAction bag();

	Stage factoryMenu();

}






