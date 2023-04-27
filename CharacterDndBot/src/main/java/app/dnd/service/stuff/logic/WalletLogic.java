package app.dnd.service.stuff.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.stuffs.Wallet;
import app.dnd.service.stuff.WalletService;
import app.bot.model.user.ActualHero;

public interface WalletLogic {

	void addCoin(ActualHero actualHero, String currency, int value);
	boolean lostCoin(ActualHero actualHero, String currency, int value);
	String menuInfo(ActualHero actualHero);
}	

@Component
class WalletFacade implements WalletLogic {

	@Autowired
	private WalletService walletService;

	@Override
	public void addCoin(ActualHero actualHero, String currency, int value) {
		
		Wallet wallet = walletService.findByUserIdAndOwnerName(actualHero.getId(), actualHero.getName());
	
		if (currency.contains("CP")) {
			wallet.setBronze(wallet.getBronze() + value);
		} else if (currency.contains("SP")) {
			wallet.setSilver(wallet.getSilver() + value);
		} else if (currency.contains("GP")) {
			wallet.setGold(wallet.getGold() + value);
		} else if (currency.contains("PP")) {
			wallet.setPlate(wallet.getPlate() + value);
		}
		
		walletService.save(wallet);
	}

	@Override
	public boolean lostCoin(ActualHero actualHero, String currency, int value) {
		
		Wallet wallet = walletService.findByUserIdAndOwnerName(actualHero.getId(), actualHero.getName());
		if (currency.contains("CP")) {
			if (wallet.getBronze() >= value) {
				wallet.setBronze(wallet.getBronze() - value);
				walletService.save(wallet);
				return true;
			}
			return false;
		} else if (currency.contains("SP")) {
			if (wallet.getSilver() >= value) {
				wallet.setSilver(wallet.getSilver() - value);
				walletService.save(wallet);
				return true;
			}
			return false;
		} else if (currency.contains("GP")) {
			if (wallet.getGold() >= value) {
				wallet.setGold(wallet.getGold() - value);
				walletService.save(wallet);
				return true;
			}
			return false;
		} else if (currency.contains("PP")) {
			if (wallet.getPlate() >= value) {
				wallet.setPlate(wallet.getPlate() - value);
				walletService.save(wallet);
				return true;
			}
			return false;
		} else {
			return false;
		}
	}

	@Override
	public String menuInfo(ActualHero actualHero) {
		return walletService.findByUserIdAndOwnerName(actualHero.getId(), actualHero.getName()).longInformation();
	} 
	
}
