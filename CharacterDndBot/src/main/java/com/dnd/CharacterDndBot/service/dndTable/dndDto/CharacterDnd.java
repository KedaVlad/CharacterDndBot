package com.dnd.CharacterDndBot.service.dndTable.dndDto;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.Ability;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.attacks.AttackAbility;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Characteristics;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Hp;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Lvl;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.Stuff;

import lombok.Data;

@Data
public class CharacterDnd implements ObjectDnd, Refreshable { 
	
	private String name;
	private int speed = 25;
	private String nature;
	@Autowired
	private Characteristics characteristics;
	private Lvl lvl;
	@Autowired
	private Hp hp;
	@Autowired
	private AttackAbility attackMachine;
	@Autowired
	private Ability ability;
	@Autowired
	private Stuff stuff;
	private RaceDnd race;
	private ClassDnd[] dndClass;
	private List<SingleAct> clouds;
	private List<String> myMemoirs;

	public CharacterDnd(String name) {
		this.name = name;
		this.myMemoirs = new ArrayList<>();
		this.clouds = new ArrayList<>();
	}

	@Override
	public void refresh(Time time) {
		// TODO Auto-generated method stub
		
	}
}
