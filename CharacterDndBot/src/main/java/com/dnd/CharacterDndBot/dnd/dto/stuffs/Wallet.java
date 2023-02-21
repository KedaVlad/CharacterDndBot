package com.dnd.CharacterDndBot.dnd.dto.stuffs;

import java.io.Serializable;

import lombok.Data;

@Data
public class Wallet implements Serializable {

	private static final long serialVersionUID = 1L;
	private int bronze = 0;// CP
	private int silver = 0;// SP
	private int gold = 0;// GP
	private int plate = 0;// PP

	public String toString() {
		return "WALLET: CP(" + bronze + ") SP(" + silver + ") GP(" + gold + ") PP(" + plate + ")";
	}
}
