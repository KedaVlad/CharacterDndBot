package com.dnd.CharacterDndBot.service.dndTable.dndDto.ability;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.Refreshable;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.features.ActiveFeature;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.features.Feature;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency.Proficiencies;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.spells.MagicSoul;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.spells.Spell;

import lombok.Data;

@Data
public class Ability implements Refreshable, Serializable {

	private static final long serialVersionUID = 1L;
	private MagicSoul magicSoul;
	private List<Feature> features;
	private Proficiencies proficiencies;

	{
		features = new ArrayList<>();
	}

	@Override
	public void refresh(Time time) {
		for (Feature feature : features) {
			if (feature instanceof ActiveFeature) {
				ActiveFeature target = (ActiveFeature) feature;
				target.refresh(time);
			}
		}
	}

}
