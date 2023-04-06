package app.dnd.service.logic.stuff.wallet;

import org.springframework.stereotype.Component;

import app.dnd.model.stuffs.Wallet;

@Component
public class WalletLostCoin {

	public boolean lost(Wallet wallet, String currency, int value) {
		
		if (currency.contains("CP")) {
			if (wallet.getBronze() >= value) {
				wallet.setBronze(wallet.getBronze() - value);
				return true;
			}
			return false;
		} else if (currency.contains("SP")) {
			if (wallet.getSilver() >= value) {
				wallet.setSilver(wallet.getSilver() - value);
				return true;
			}
			return false;
		} else if (currency.contains("GP")) {
			if (wallet.getGold() >= value) {
				wallet.setGold(wallet.getGold() - value);
				return true;
			}
			return false;
		} else if (currency.contains("PP")) {
			if (wallet.getPlate() >= value) {
				wallet.setPlate(wallet.getPlate() - value);
				return true;
			}
			return false;
		} else {
			return false;
		}
	}
}
