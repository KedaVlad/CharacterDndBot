package com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs;
 
import java.util.ArrayList;
import java.util.List;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.ObjectDnd; 
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Armor;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Items; 

import lombok.Data;

@Data
public class Stuff implements ObjectDnd { 
	
	private static final long serialVersionUID = 1L;
	private Wallet wallet;
	private List<Items> insideBag;
	private List<String> status;
	private List<Items> prepeared;
	private Armor[] weared = new Armor[2];

	{
		status = new ArrayList<>();
		prepeared = new ArrayList<>();
		insideBag = new ArrayList<>();
	}
	
}
