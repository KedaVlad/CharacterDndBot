package com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Lvl;

public class LvlLogic implements CharacterLogic {

	private Lvl lvl;
	@Override
	public void initialize(CharacterDnd character) {
		this.lvl = character.getLvl();
	}
	
	//return true if level up
	public boolean addExperience(int value)
	{
		lvl.setExperience(lvl.getExperience() + value);
		for(int i = lvl.getExpPerLvl().length - 1; i > 0; i--)
		{
			if(lvl.getExpPerLvl()[i] <= lvl.getExperience())
			{
				if(lvl.getLvl() != i+1)
				{
				this.lvl.setLvl(i + 1);
				return true;
				}
				return false;
			}
		}
		return false;
	}

	public String info() 
	{
		String answer = "LVL: " + lvl.getLvl() + "(" + lvl.getExperience() + "|" + lvl.getExpPerLvl()[lvl.getLvl()] + ")";
		if(lvl.getLvl() == 20)
		{
			answer = "LVL: 20(MAX LVL)";
		}
		return answer + "\n";
	}
	
}
