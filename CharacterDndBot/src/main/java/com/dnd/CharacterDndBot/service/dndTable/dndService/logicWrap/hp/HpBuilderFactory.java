package com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap.hp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ClassDnd;

@Service
public class HpBuilderFactory {

	@Autowired
	HpStableBuilder hpStableBuilder;
	@Autowired
	HpRandomBuilder hpRandomBuilder;
	
	public HpStableBuilder stable() {
		return hpStableBuilder;
	}
	
	public HpRandomBuilder random() {
		return hpRandomBuilder;
	}
}

interface HpBuilder {
	public abstract int buildForLvlUp(CharacterDnd character, ClassDnd clazz);
	public abstract int buildBase(CharacterDnd character);
}

