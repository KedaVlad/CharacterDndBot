package com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs;

import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
public class Wallet {

	private int bronze = 0;// CP
	private int silver = 0;// SP
	private int gold = 0;// GP
	private int plate = 0;// PP

	public void addCoin(String currency, int value) {
		if (currency.contains("CP")) {
			bronze += value;
		} else if (currency.contains("SP")) {
			silver += value;
		} else if (currency.contains("GP")) {
			gold += value;
		} else if (currency.contains("PP")) {
			plate += value;
		}
	}

	public boolean lostCoin(String currency, int value) {
		if (currency.contains("CP")) {
			if (bronze >= value) {
				bronze -= value;
				return true;
			}
			return false;
		} else if (currency.contains("SP")) {
			if (silver >= value) {
				silver -= value;
				return true;
			}
			return false;
		} else if (currency.contains("GP")) {
			if (gold >= value) {
				gold -= value;
				return true;
			}
			return false;
		} else if (currency.contains("PP")) {
			if (plate >= value) {
				plate -= value;
				return true;
			}
			return false;
		} else {
			return false;
		}
	}

	public String toString() {
		return "WALLET: CP(" + bronze + ") SP(" + silver + ") GP(" + gold + ") PP(" + plate + ")";
	}
}
