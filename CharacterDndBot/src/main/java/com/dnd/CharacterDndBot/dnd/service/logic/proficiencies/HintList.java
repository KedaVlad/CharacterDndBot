package com.dnd.CharacterDndBot.dnd.service.logic.proficiencies;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.dnd.dto.stuffs.items.Armor.Armors;
import com.dnd.CharacterDndBot.dnd.dto.stuffs.items.Tool.Tools;
import com.dnd.CharacterDndBot.dnd.dto.stuffs.items.Weapon.Weapons;

@Component
public class HintList {

	public String build() {
		
		String answer = "";
		answer += "Weapon: ";
		for (int i = 0; i < Weapons.values().length; i++) {
			if (i == Weapons.values().length - 1) {
				answer += Weapons.values()[i].toString() + ".";
			} else {
				answer += Weapons.values()[i].toString() + ", ";
			}
		}
		answer += "\nTool: ";
		for (int i = 0; i < Tools.values().length; i++) {
			if (i == Tools.values().length - 1) {
				answer += Tools.values()[i].toString() + ".";
			} else {
				answer += Tools.values()[i].toString() + ", ";
			}
		}
		answer += "\nArmor: ";
		for (int i = 0; i < Armors.values().length; i++) {
			if (i == Armors.values().length - 1) {
				answer += Armors.values()[i].toString() + ".";
			} else {
				answer += Armors.values()[i].toString() + ", ";
			}
		}
		return answer;
	}
}
