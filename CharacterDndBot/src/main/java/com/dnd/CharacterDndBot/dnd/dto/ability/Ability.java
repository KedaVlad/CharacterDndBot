package com.dnd.CharacterDndBot.dnd.dto.ability;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.CharacterDndBot.dnd.dto.Refreshable;
import com.dnd.CharacterDndBot.dnd.dto.ability.features.ActiveFeature;
import com.dnd.CharacterDndBot.dnd.dto.ability.features.Feature;
import com.dnd.CharacterDndBot.dnd.dto.ability.proficiency.Proficiencies;
import com.dnd.CharacterDndBot.dnd.dto.ability.spells.MagicSoul;

import lombok.Data;

@Data
public class Ability implements Refreshable, Serializable {

	private static final long serialVersionUID = 1L;
	private MagicSoul magicSoul;
	private List<Feature> features;
	private Proficiencies proficiencies;

	{
		features = new ArrayList<>();
		proficiencies = new Proficiencies();
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
