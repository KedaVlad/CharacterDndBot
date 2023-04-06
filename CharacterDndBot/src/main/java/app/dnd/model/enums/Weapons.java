package app.dnd.model.enums;

import app.dnd.model.ability.attacks.AttackModification;
import app.dnd.util.math.DamageDice;
import app.dnd.util.math.DamageDice.TypeDamage;
import app.dnd.util.math.Formalizer.Roll;
import lombok.Getter;

public enum Weapons {
	HALBERD("Halberd", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D10))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.HEAVY, WeaponProperties.AVAILABILITY, WeaponProperties.MILITARY, WeaponProperties.TWO_HANDED, WeaponProperties.MELEE)
			.build()),
	
	WARHAMER("Warhamer", AttackModification.builder()
			.name("One heand attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D8))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE)
			.build(),
			AttackModification.builder()
			.name("Two heand attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D10))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)
			.build()),
	
	QUARTERSTAFF("Quarterstaff", AttackModification.builder()
			.name("One heand attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D6))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.MELEE)
			.build(),
			AttackModification.builder()
			.name("Two heand attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D8))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)
			.build()),
	
	BATTLEAXE("Battleaxe", AttackModification.builder()
			.name("One heand attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D8))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE)
			.build(),
			AttackModification.builder()
			.name("Two heand attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D10))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)
			.build()),
	
	MACE("Mace", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D6))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.MELEE)
			.build()),
	
	GYTHKA("Gythka", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D8))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)
			.build()),
	
	
	GLAIVE("Glaive", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D10))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.AVAILABILITY, WeaponProperties.HEAVY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)
			.build()),
	
	DOUBLE_BLADET_SCIMITAR("Double-blade scimitar", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D4, Roll.D4))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)
			.build()),
	
	GREATWSWORD("Greatesword", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D6, Roll.D6))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.HEAVY, WeaponProperties.TWO_HANDED)
			.build()),
	
	MAUL("Maul", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D6, Roll.D6))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.HEAVY, WeaponProperties.TWO_HANDED)
			.build()),
	
	GREATEAXE("Greateaxe", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D12))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.HEAVY, WeaponProperties.TWO_HANDED)
			.build()),
	 
	LONGBOW("Longbow", AttackModification.builder()
			.name("Range attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D12))
			.mainStat(Stats.DEXTERITY)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE, WeaponProperties.HEAVY, WeaponProperties.TWO_HANDED)
			.ammunition(Ammunitions.ARROWS)
			.build()),
	
	LONGSWORD("Longsword", AttackModification.builder()
			.name("One heand attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D8))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE)
			.build(),
			AttackModification.builder()
			.name("Two heand attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D10))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)
			.build()),
	
	DART("Dart", AttackModification.builder()
			.name("Throw")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D4))
			.mainStat(Stats.DEXTERITY)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.THROWING, WeaponProperties.AMMUNITION)
			.build()),
	
	GREATECLUB("Greateclub", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D8))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)
			.build()),
	
	CLUB("Club", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D4))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.LUNG)
			.secondStat(Stats.DEXTERITY)
			.build()),
	 
	
	BLOWGUN("Blowgun", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 1, TypeDamage.STICKING, Roll.NO_ROLL))
			.mainStat(Stats.DEXTERITY)
			.requirement(WeaponProperties.LONG_RANGE, WeaponProperties.AMMUNITION, WeaponProperties.MILITARY)
			.ammunition(Ammunitions.BLOWWGUN_NEEDLES)
			.build()),
	 
	YKLWA("Yklwa", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.MELEE)
			.build(),
			AttackModification.builder()
			.name("Throw")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.THROWING)
			.build()),
	
	LANCE("Lance", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D12))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.AVAILABILITY)
			.build()),
	
	DAGGER("Dagger", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D4))
			.mainStat(Stats.STRENGTH)
			.secondStat(Stats.DEXTERITY)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.FENCING, WeaponProperties.LUNG)
			.build(),
			AttackModification.builder()
			.name("Throw")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D4))
			.mainStat(Stats.STRENGTH)
			.secondStat(Stats.DEXTERITY)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.THROWING)
			.build()),
	
	WAR_PICK("War pick", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE)
			.build()),
	
	WHIP("Whip", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D4))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.AVAILABILITY, WeaponProperties.FENCING)
			.secondStat(Stats.DEXTERITY)
			.build()),
		
	SPEAR("Spear", AttackModification.builder()
			.name("One heand attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.MELEE)
			.build(),
			AttackModification.builder()
			.name("Throw")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.THROWING)
			.build(),
			AttackModification.builder()
			.name("Two heand attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)
			.build()),
	
	SHORTBOW("Shortbow", AttackModification.builder()
			.name("Range attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6))
			.mainStat(Stats.DEXTERITY)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE)
			.ammunition(Ammunitions.ARROWS)
			.build()),
	
	SHORTSWORD("Shortsword", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.FENCING, WeaponProperties.LUNG)
			.secondStat(Stats.DEXTERITY)
			.build()),
			
	CROSSBOW_LIGHT("Crossbow, light", AttackModification.builder()
			.name("Range attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8))
			.mainStat(Stats.DEXTERITY)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE)
			.ammunition(Ammunitions.CROSSBOW_BOLTS)
			.build()),
	
	LIGHT_HUMMER("Light hummer", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D4))
			.mainStat(Stats.STRENGTH)
			.secondStat(Stats.DEXTERITY)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.FENCING, WeaponProperties.LUNG)
			.build(),
			AttackModification.builder()
			.name("Throw")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D4))
			.mainStat(Stats.STRENGTH)
			.secondStat(Stats.DEXTERITY)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.THROWING)
			.build()),
			
	MORNINGSTAR("Morningstar", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE)
			.build()),
	
	PIKE("Pike", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D10))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.AVAILABILITY, WeaponProperties.TWO_HANDED)
			.build()),
	
	JAVELIN("Javelin", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.MELEE)
			.build(),
			AttackModification.builder()
			.name("Throw")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.THROWING)
			.build()),
			
	SLING("Sling", AttackModification.builder()
			.name("Range attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D4))
			.mainStat(Stats.DEXTERITY)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE)
			.ammunition(Ammunitions.SLING_BULLETS)
			.build()),
	
	RAPIER("Rapier", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.FENCING)
			.secondStat(Stats.DEXTERITY)
			.build()),
	
	CROSSBOW_HAND("Crossbow, hand", AttackModification.builder()
			.name("Range attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6))
			.mainStat(Stats.DEXTERITY)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE, WeaponProperties.LUNG)
			.ammunition(Ammunitions.CROSSBOW_BOLTS)
			.build()),
	
	SICKLE("Sickle", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D4))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.LUNG)
			.build()),
	
	SCIMITAR("Scimitar", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D6))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.FENCING, WeaponProperties.LUNG)
			.secondStat(Stats.DEXTERITY)
			.build()),
	
	HANDAXE("Handaxe", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D6))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.LUNG)
			.build(),
			AttackModification.builder()
			.name("Throw")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D6))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.SIMPLE, WeaponProperties.THROWING)
			.build()),		
	
	TRIDEN("Triden", AttackModification.builder()
			.name("One heand attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.THROWING)
			.build(),
			AttackModification.builder()
			.name("Two heand attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)
			.build()),
	
	CROSSBOW_HEAVY("Crossbow, heavy", AttackModification.builder()
			.name("Range attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D10))
			.mainStat(Stats.DEXTERITY)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE, WeaponProperties.HEAVY, WeaponProperties.TWO_HANDED)
			.ammunition(Ammunitions.CROSSBOW_BOLTS)
			.build()),
	
	FLAIL("Flail", AttackModification.builder()
			.name("Melee attack")
			.damage(new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D8))
			.mainStat(Stats.STRENGTH)
			.requirement(WeaponProperties.MILITARY, WeaponProperties.MELEE)
			.build());
	
	
	Weapons(String name, AttackModification... attackTypes) {
		this.name = name;
		this.attackTypes = attackTypes;
	}

	@Getter
	private String name;
	@Getter
	private AttackModification[] attackTypes;

	public String toString() {
		return name;
	}

}

