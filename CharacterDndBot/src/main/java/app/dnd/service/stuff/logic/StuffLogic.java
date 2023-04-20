package app.dnd.service.stuff.logic;

import app.user.model.ActualHero;

public interface StuffLogic {
	
	String stuffMenuInfo(ActualHero actualHero);

	WalletLogic wallet();
	
	BagLogic bag();
	
}
