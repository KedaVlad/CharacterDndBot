package com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
public class Proficiencies { 
	
	private int proficiency;
	private List<Possession> possessions;

	public enum Proficiency {
		BASE, HALF, COMPETENSE
	}
	
	{
		possessions = new ArrayList<>();
		proficiency = 2;
	}
}