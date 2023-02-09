package com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Armor;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Items;

import lombok.Data;

@Data
@Component
public class CarryingStuff{

	private boolean barbarian;
	private List<String> status;
	private List<Items> prepeared;
	private Armor[] weared = new Armor[2];

	{
		status = new ArrayList<>();
		prepeared = new ArrayList<>();
	}
}
