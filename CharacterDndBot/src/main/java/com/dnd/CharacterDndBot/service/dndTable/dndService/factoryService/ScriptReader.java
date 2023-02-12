package com.dnd.CharacterDndBot.service.dndTable.dndService.factoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ObjectDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.features.Feature;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.comands.AddComand;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.comands.CloudComand;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.comands.InerComand;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.comands.UpComand;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Items;

@Service
public class ScriptReader
{
	@Autowired
	private CloudExecutor cloudExecutor;
	@Autowired
	private UpComandExecutor upComandExecutor;
	@Autowired	
	private AddComandExecutor addComandExecutor;

	public void execute(CharacterDnd character, InerComand comand)
	{
		if(comand instanceof AddComand)
		{
			addComandExecutor.add(character, (AddComand) comand);
		}
		else if(comand instanceof UpComand)
		{
			upComandExecutor.up(character,(UpComand) comand);
		}
		else if(comand instanceof CloudComand)
		{
			cloudExecutor.cloud(character,(CloudComand) comand);
		}
	}

	@Component
	class CloudExecutor {

		public void cloud(CharacterDnd character, CloudComand comand) 
		{
			character.getClouds().add(SingleAct.builder()
					.name(comand.getName())
					.text(comand.getText())
					.action(Action.builder().cloud().build())
					.build());

		}
	}

	@Component
	class UpComandExecutor {

		public void up(CharacterDnd character, UpComand comand) 
		{

		}
	}

	@Component
	class AddComandExecutor {

		private void add(CharacterDnd character, AddComand comand) 
		{
			ObjectDnd[] objects = comand.getTargets();
			for(ObjectDnd object: objects)
			{
				if(object instanceof Items)
				{
					character.getStuff().getInsideBag().add((Items) object);
					break;
				}
				else if(object instanceof Feature)
				{
					character.getAbility().getFeatures().add((Feature) object);
					if(object instanceof InerFeature)
					{
						InerFeature target = (InerFeature) object;
						for(InerComand inerComand: target.getComand())
						{
							execute(character, inerComand);
						}
					}
					break;
				}
				else if(object instanceof Possession)
				{
					Possession target = (Possession) object;
					if(target.getName().matches("^SR.*"))
					{
						for(Skill article: character.getRolls().getSaveRolls())
						{
							if(article.getName().equals(target.getName()))
							{
								article.setProficiency(target.getProf());
								break;
							}
						}
					}
					else
					{
						for(Skill article: character.getRolls().getSkills())
						{
							if(article.getName().equals(target.getName()))
							{
								article.setProficiency(target.getProf());
								break;
							}
						}
					}
					character.getWorkmanship().getPossessions().add(target);
				}
				else if(object instanceof MagicSoul)
				{
					MagicSoul target = (MagicSoul) object;
					character.getWorkmanship().setMagicSoul(target);
				}
				else if(object instanceof Spell)
				{
					Spell target = (Spell) object;
					if(character.getWorkmanship().getMagicSoul() != null)
					{
						if(target.getLvlSpell() == 0)
						{
							character.getWorkmanship().getMagicSoul().getPoolCantrips().add(target);
						}
						else
						{
							character.getWorkmanship().getMagicSoul().getPool().add(target);
						}
					}
				}
				else if(object instanceof AttackModification)
				{

					AttackModification target = (AttackModification) object;
					if(target.isPostAttack())
					{
						if(character.getAttackMachine().getAfterAttak().contains(target))
						{
							character.getAttackMachine().getAfterAttak().remove(target);
							character.getAttackMachine().getAfterAttak().add(target);
						}
						else
						{
							character.getAttackMachine().getAfterAttak().add(target);
						}
					}
					else if(target.isPermanent())
					{
						if(character.getAttackMachine().getPermanent().contains(target))
						{
							character.getAttackMachine().getPermanent().remove(target);
							character.getAttackMachine().getPermanent().add(target);
						}
						else
						{
							character.getAttackMachine().getPermanent().add(target);
						}
					}
					else
					{
						if(character.getAttackMachine().getPreAttacks().contains(target))
						{
							character.getAttackMachine().getPreAttacks().remove(target);
							character.getAttackMachine().getPreAttacks().add(target);
						}
						else
						{
							character.getAttackMachine().getPreAttacks().add(target);
						}
					}
				}
			}
		}
	}
}