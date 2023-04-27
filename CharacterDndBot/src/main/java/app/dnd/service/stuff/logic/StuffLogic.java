package app.dnd.service.stuff.logic;

import app.bot.model.user.ActualHero;

public interface StuffLogic {
	
	String stuffMenuInfo(ActualHero actualHero);

	WalletLogic wallet();
	
	BagLogic bag();
	
}
