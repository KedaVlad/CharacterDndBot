package app.dnd.service.logic.stuff.wallet;

import org.springframework.stereotype.Component;

import app.dnd.dto.stuffs.Wallet;

@Component
public class WalletAddCoin {

	public void add(Wallet wallet, String currency, int value) {
		if (currency.contains("CP")) {
			wallet.setBronze(wallet.getBronze() + value);
		} else if (currency.contains("SP")) {
			wallet.setSilver(wallet.getSilver() + value);
		} else if (currency.contains("GP")) {
			wallet.setGold(wallet.getGold() + value);
		} else if (currency.contains("PP")) {
			wallet.setPlate(wallet.getPlate() + value);
		}
	}
}
