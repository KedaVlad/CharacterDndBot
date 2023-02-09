package com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs;
 
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.ObjectDnd; 
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Armor;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Items; 

import lombok.Data;

@Data
@Component
public class Stuff implements ObjectDnd { 
	@Autowired
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
