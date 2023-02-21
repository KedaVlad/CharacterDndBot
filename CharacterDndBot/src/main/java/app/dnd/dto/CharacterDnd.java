package app.dnd.dto;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import app.bot.model.act.SingleAct;
import app.dnd.dto.ability.Ability;
import app.dnd.dto.ability.attacks.AttackAbility;
import app.dnd.dto.characteristics.Characteristics;
import app.dnd.dto.characteristics.Hp;
import app.dnd.dto.characteristics.Lvl;
import app.dnd.dto.stuffs.Stuff;
import lombok.Data;

@Data
public class CharacterDnd implements Informator, Serializable , Refreshable { 
	 
	private static final long serialVersionUID = 1L;
	private String name;
	private int speed = 25;
	private String nature;
	private Characteristics characteristics;
	private Lvl lvl;
	private Hp hp;
	private AttackAbility attackMachine;
	private Ability ability;
	private Stuff stuff;
	private RaceDnd race;
	private List<ClassDnd> dndClass;
	private List<SingleAct> clouds;
	private List<String> myMemoirs;

	public CharacterDnd(String name) {
		this.name = name;
		this.dndClass = new ArrayList<>();
		this.myMemoirs = new ArrayList<>();
		this.clouds = new ArrayList<>();
		this.hp = new Hp();
		this.lvl = new Lvl();
		this.characteristics = new Characteristics();
		this.attackMachine = new AttackAbility();
		this.ability = new Ability();
		this.stuff = new Stuff();
	}

	@Override
	public void refresh(Time time) {
		// TODO Auto-generated method stub
		
	}
}
