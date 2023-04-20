package app.dnd.service.stuff.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.stuffs.Stuff;
import app.dnd.service.stuff.StuffService;
import app.dnd.service.stuff.WalletService;
import app.player.model.enums.Button;
import app.user.model.ActualHero;

@Component
public class StuffFacade implements StuffLogic {
	
	@Autowired
	private WalletLogic walletLogic;
	@Autowired
	private BagLogic bagLogic;
	@Autowired
	private StuffService stuffService;
	@Autowired
	private WalletService walletService;
	
	@Override
	public String stuffMenuInfo(ActualHero actualHero) {
		
		Stuff stuff = stuffService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		return "Here, you'll be able to access the hero`s "+ Button.WALLET.NAME +", "+Button.BAG.NAME+", and any items they have in their possession("+Button.CARRYING_STUFF.NAME+")."
				+"\n"
				+"\n" + Button.WALLET.NAME  + " " + walletService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName()).shortInformation()
				+"\n" + Button.BAG.NAME + " ("+ stuff.getInsideBag().size()+")"
				+"\n" + Button.CARRYING_STUFF.NAME +" (" + stuff.getItemsInHand().size()+")";
	}

	@Override
	public WalletLogic wallet() {
		return walletLogic;
	}

	@Override
	public BagLogic bag() {
		return bagLogic;
	}

	
}
