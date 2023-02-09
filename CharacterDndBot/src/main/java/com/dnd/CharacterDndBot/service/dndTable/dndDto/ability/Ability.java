package com.dnd.CharacterDndBot.service.dndTable.dndDto.ability;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.Refreshable;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.features.ActiveFeature;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.features.Feature;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency.Proficiencies;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.spells.MagicSoul;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.spells.Spell;

import lombok.Data;

@Data
@Component
public class Ability implements Refreshable{

	private MagicSoul magicSoul;
	private List<Feature> myFeatures;
	private List<Spell> mySpells;
	@Autowired
	private Proficiencies proficiencies;

	{
		myFeatures = new ArrayList<>();
		mySpells = new ArrayList<>();
	}

	@Override
	public void refresh(Time time) {
		for (Feature feature : myFeatures) {
			if (feature instanceof ActiveFeature) {
				ActiveFeature target = (ActiveFeature) feature;
				target.refresh(time);
			}
		}
	}

}
