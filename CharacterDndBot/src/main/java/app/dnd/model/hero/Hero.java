package app.dnd.model.hero;

import app.dnd.model.ability.Ability;
import app.dnd.model.ability.attacks.AttackAbility;
import app.dnd.model.ability.proficiency.Proficiencies;
import app.dnd.model.ability.spells.MagicSoul;
import app.dnd.model.characteristics.Characteristics;
import app.dnd.model.characteristics.Hp;
import app.dnd.model.characteristics.Lvl;
import app.dnd.model.characteristics.Memoirs;
import app.dnd.model.stuffs.Stuff;
import app.dnd.model.stuffs.Wallet;
import lombok.Data;

@Data
public class Hero { 
	 
	private CharacterDnd character;
	private Characteristics characteristics;
	private Lvl lvl;
	private Hp hp;
	private AttackAbility attackAbility;
	private Proficiencies proficiencies;
	private Ability ability;
	private Stuff stuff;
	private Wallet wallet;
	private RaceDnd race;
	private ClassesDnd classesDnd;
	private Memoirs memoirs;
	private MagicSoul magicSoul;
}