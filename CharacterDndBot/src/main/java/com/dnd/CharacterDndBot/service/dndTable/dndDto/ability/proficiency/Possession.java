package com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency;
 
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ObjectDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency.Proficiencies.Proficiency;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;

@JsonTypeName("POSSESSION")
@Data
public class Possession implements ObjectDnd {

	private Proficiency prof;
	private String name;
	
	public Possession() {}

	public Possession(String name) {
		this.name = name;
		this.prof = Proficiency.BASE;
	}
	
	public Possession(String name, Proficiency prof) {
		this.name = name;
		this.prof = prof;
	}
}
