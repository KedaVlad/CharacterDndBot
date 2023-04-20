package app.dnd.model.hero;

import app.dnd.model.ability.Ability;
import app.dnd.model.ability.Hp;
import app.dnd.model.ability.Lvl;
import app.dnd.model.ability.Memoirs;
import app.dnd.model.stuffs.Stuff;
import app.dnd.model.stuffs.Wallet;
import app.dnd.model.telents.attacks.AttackAbility;
import app.dnd.model.telents.features.Features;
import app.dnd.model.telents.proficiency.Proficiencies;
import app.dnd.model.telents.spells.MagicSoul;
import lombok.Data;

@Data
public class Hero { 
	
	private Long id;
	private String name;
	private HeroCloud heroCloud;
	private Ability characteristics;
	private Lvl lvl;
	private Hp hp;
	private AttackAbility attackAbility;
	private Proficiencies proficiencies;
	private Features ability;
	private Stuff stuff;
	private Wallet wallet;
	private RaceDnd race;
	private ClassesDnd classesDnd;
	private Memoirs memoirs;
	private MagicSoul magicSoul;
}