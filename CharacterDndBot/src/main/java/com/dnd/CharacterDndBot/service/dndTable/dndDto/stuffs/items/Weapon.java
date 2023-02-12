package com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items;


import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.attacks.AttackModification;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Stat.Stats;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Ammunition.Ammunitions;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.DamageDice;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.DamageDice.TypeDamage;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Formalizer.Roll;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("WEAPON")
@Data
@EqualsAndHashCode(callSuper=false)
public class Weapon extends Items {
 
	private static final long serialVersionUID = 1L;
	private int attack;
	private int damage;

	public enum WeaponProperties {
		UNIVERSAL, LUNG, THROWING, FENCING, TWO_HANDED, AMMUNITION, RELOAD, AVAILABILITY, MILITARY, LONG_RANGE, MELEE,
		SIMPLE, HEAVY
	}

	private Weapons type;

	public Weapon() {
	}

	public Weapon(Weapons type) {
		this.setName(type.name);
		this.setDescription(descripter(type));
		this.type = type;
	}

	private String descripter(Weapons type) {
		String answer = type.name + "\n";
		for (AttackModification typeAttack : type.attackTypes) {
			answer += typeAttack.toString() + "\n";
		}
		return answer;
	}

	public String toString() {
		if (attack != 0 || damage != 0) {
			return getName() + "(" + attack + "|" + damage + ")";
		}
		return getName();
	}

	public enum Weapons {
		HALBERD("Halberd", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D10))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.HEAVY, WeaponProperties.AVAILABILITY, WeaponProperties.MILITARY, WeaponProperties.TWO_HANDED, WeaponProperties.MELEE)
				.build()),
		
		WARHAMER("Warhamer", AttackModification.builder()
				.name("One heand attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D8))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE)
				.build(),
				AttackModification.builder()
				.name("Two heand attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D10))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)
				.build()),
		
		QUARTERSTAFF("Quarterstaff", AttackModification.builder()
				.name("One heand attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D6))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.MELEE)
				.build(),
				AttackModification.builder()
				.name("Two heand attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D8))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)
				.build()),
		
		BATTLEAXE("Battleaxe", AttackModification.builder()
				.name("One heand attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D8))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE)
				.build(),
				AttackModification.builder()
				.name("Two heand attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D10))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)
				.build()),
		
		MACE("Mace", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D6))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.MELEE)
				.build()),
		
		GYTHKA("Gythka", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D8))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)
				.build()),
		
		
		GLAIVE("Glaive", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D10))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.AVAILABILITY, WeaponProperties.HEAVY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)
				.build()),
		
		DOUBLE_BLADET_SCIMITAR("Double-blade scimitar", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D4, Roll.D4))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)
				.build()),
		
		GREATWSWORD("Greatesword", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D6, Roll.D6))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.HEAVY, WeaponProperties.TWO_HANDED)
				.build()),
		
		MAUL("Maul", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D6, Roll.D6))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.HEAVY, WeaponProperties.TWO_HANDED)
				.build()),
		
		GREATEAXE("Greateaxe", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D12))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.HEAVY, WeaponProperties.TWO_HANDED)
				.build()),
		 
		LONGBOW("Longbow", AttackModification.builder()
				.name("Range attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D12))
				.mainStat(Stats.DEXTERITY)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE, WeaponProperties.HEAVY, WeaponProperties.TWO_HANDED)
				.ammunition(Ammunitions.ARROWS)
				.build()),
		
		LONGSWORD("Longsword", AttackModification.builder()
				.name("One heand attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D8))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE)
				.build(),
				AttackModification.builder()
				.name("Two heand attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D10))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)
				.build()),
		
		DART("Dart", AttackModification.builder()
				.name("Throw")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D4))
				.mainStat(Stats.DEXTERITY)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.THROWING, WeaponProperties.AMMUNITION)
				.build()),
		
		GREATECLUB("Greateclub", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D8))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)
				.build()),
		
		CLUB("Club", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D4))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.LUNG)
				.secondStat(Stats.DEXTERITY)
				.build()),
		 
		
		BLOWGUN("Blowgun", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 1, TypeDamage.STICKING, Roll.NO_ROLL))
				.mainStat(Stats.DEXTERITY)
				.requirment(WeaponProperties.LONG_RANGE, WeaponProperties.AMMUNITION, WeaponProperties.MILITARY)
				.ammunition(Ammunitions.BLOWWGUN_NEEDLES)
				.build()),
		 
		YKLWA("Yklwa", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.MELEE)
				.build(),
				AttackModification.builder()
				.name("Throw")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.THROWING)
				.build()),
		
		LANCE("Lance", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D12))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.AVAILABILITY)
				.build()),
		
		DAGGER("Dagger", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D4))
				.mainStat(Stats.STRENGTH)
				.secondStat(Stats.DEXTERITY)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.FENCING, WeaponProperties.LUNG)
				.build(),
				AttackModification.builder()
				.name("Throw")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D4))
				.mainStat(Stats.STRENGTH)
				.secondStat(Stats.DEXTERITY)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.THROWING)
				.build()),
		
		WAR_PICK("War pick", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE)
				.build()),
		
		WHIP("Whip", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D4))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.AVAILABILITY, WeaponProperties.FENCING)
				.secondStat(Stats.DEXTERITY)
				.build()),
			
		SPEAR("Spear", AttackModification.builder()
				.name("One heand attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.MELEE)
				.build(),
				AttackModification.builder()
				.name("Throw")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.THROWING)
				.build(),
				AttackModification.builder()
				.name("Two heand attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)
				.build()),
		
		SHORTBOW("Shortbow", AttackModification.builder()
				.name("Range attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6))
				.mainStat(Stats.DEXTERITY)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE)
				.ammunition(Ammunitions.ARROWS)
				.build()),
		
		SHORTSWORD("Shortsword", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.FENCING, WeaponProperties.LUNG)
				.secondStat(Stats.DEXTERITY)
				.build()),
				
		CROSSBOW_LIGHT("Crossbow, light", AttackModification.builder()
				.name("Range attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8))
				.mainStat(Stats.DEXTERITY)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE)
				.ammunition(Ammunitions.CROSSBOW_BOLTS)
				.build()),
		
		LIGHT_HUMMER("Light hummer", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D4))
				.mainStat(Stats.STRENGTH)
				.secondStat(Stats.DEXTERITY)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.FENCING, WeaponProperties.LUNG)
				.build(),
				AttackModification.builder()
				.name("Throw")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D4))
				.mainStat(Stats.STRENGTH)
				.secondStat(Stats.DEXTERITY)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.THROWING)
				.build()),
				
		MORNINGSTAR("Morningstar", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE)
				.build()),
		
		PIKE("Pike", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D10))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.AVAILABILITY, WeaponProperties.TWO_HANDED)
				.build()),
		
		JAVELIN("Javelin", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.MELEE)
				.build(),
				AttackModification.builder()
				.name("Throw")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.THROWING)
				.build()),
				
		SLING("Sling", AttackModification.builder()
				.name("Range attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D4))
				.mainStat(Stats.DEXTERITY)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE)
				.ammunition(Ammunitions.SLING_BULLETS)
				.build()),
		
		RAPIER("Rapier", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.FENCING)
				.secondStat(Stats.DEXTERITY)
				.build()),
		
		CROSSBOW_HAND("Crossbow, hand", AttackModification.builder()
				.name("Range attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6))
				.mainStat(Stats.DEXTERITY)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE, WeaponProperties.LUNG)
				.ammunition(Ammunitions.CROSSBOW_BOLTS)
				.build()),
		
		SICKLE("Sickle", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D4))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.LUNG)
				.build()),
		
		SCIMITAR("Scimitar", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D6))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.FENCING, WeaponProperties.LUNG)
				.secondStat(Stats.DEXTERITY)
				.build()),
		
		HANDAXE("Handaxe", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D6))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.LUNG)
				.build(),
				AttackModification.builder()
				.name("Throw")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D6))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.SIMPLE, WeaponProperties.THROWING)
				.build()),		
		
		TRIDEN("Triden", AttackModification.builder()
				.name("One heand attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.THROWING)
				.build(),
				AttackModification.builder()
				.name("Two heand attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)
				.build()),
		
		CROSSBOW_HEAVY("Crossbow, heavy", AttackModification.builder()
				.name("Range attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D10))
				.mainStat(Stats.DEXTERITY)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE, WeaponProperties.HEAVY, WeaponProperties.TWO_HANDED)
				.ammunition(Ammunitions.CROSSBOW_BOLTS)
				.build()),
		
		FLAIL("Flail", AttackModification.builder()
				.name("Melee attack")
				.addDamage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D8))
				.mainStat(Stats.STRENGTH)
				.requirment(WeaponProperties.MILITARY, WeaponProperties.MELEE)
				.build());
		
		
		Weapons(String name, AttackModification... attackTypes) {
			this.name = name;
			this.attackTypes = attackTypes;
		}

		private String name;
		private AttackModification[] attackTypes;

		public String toString() {
			return name;
		}

	}

}
