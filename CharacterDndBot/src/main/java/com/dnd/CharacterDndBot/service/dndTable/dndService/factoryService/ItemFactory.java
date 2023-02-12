package com.dnd.CharacterDndBot.service.dndTable.dndService.factoryService;

import java.util.ArrayList;
import java.util.List;

import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.bot.user.User;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Ammunition;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Armor;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Items;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Pack;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Tool;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Weapon;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Ammunition.Ammunitions;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Armor.Armors;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Pack.Packs;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Tool.Tools;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Weapon.Weapons;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Location;

public class ItemFactory extends Executor<Action> {

	public ItemFactory(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		if (action.condition() == 0) {
			return startCreate();
		} else {
			String target = action.getAnswers()[0];
			switch(target)
			{
			case WEAPON_B:
			return null;
			case AMNUNITION_B:
				return null;
			case TOOL_B:
				return null;
			case PACK_B:
				return null;
			case ARMOR_B:
				return null;
			case ELSE_B:
				return null;
				default:
					return null;
			}
		}
	}

	public Act startCreate() {
		return SingleAct.builder()
				.name("CreateItem")
				.text("Which item you take?")
				.action(Action.builder()
						.location(Location.ITEM_FACTORY)
						.buttons(new String[][]
								{{WEAPON_B, AMNUNITION_B},
							{TOOL_B, PACK_B},
							{ARMOR_B},
							{ELSE_B}})
						.build())
				.build();
	}
	
	static Act finish(Action action, CharacterDnd actualGameCharacter) {
		actualGameCharacter.getStuff().getInsideBag().add((Items)action.getObjectDnd());
		return ReturnAct.builder().target(STUFF_B).call(BAG_B).build();
	}

	static String[][] arrayToThreeColums(Object[] array) {
		String[] all = new String[array.length];
		for (int i = 0; i < all.length; i++) {
			all[i] = array[i].toString();
		}
		List<String[]> buttons = new ArrayList<>();

		for (int i = 1; i <= all.length; i += 3) {
			if (((i + 1) > all.length) && ((i + 2) > all.length)) {
				buttons.add(new String[] { all[i - 1] });
			} else if ((i + 2) > all.length) {
				buttons.add(new String[] { all[i - 1], all[i] });
			} else {
				buttons.add(new String[] { all[i - 1], all[i], all[i + 1] });
			}
		}
		String[][] allRaces = buttons.toArray(new String[buttons.size()][]);
		return allRaces;
	}

}

class ElseMenu extends Executor<Action> {

	public ElseMenu(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		switch (action.getAnswers().length) {
		case 1:
			return	chooseNameItems();
		case 2:
			return  chooseDescriptionItems();
		case 3:
			return  agreedItems();
		case 4:
			return ItemFactory.finish(action, user.getCharactersPool().getActual());
		}
		return null;
	}

	private SingleAct chooseNameItems() {
		action.setMediator(true);
		return SingleAct.builder()
				.name("chooseNameItems")
				.text("How would you name this item?(Write)")
				.action(action)
				.build();
	}

	private SingleAct chooseDescriptionItems() 
	{
		action.setMediator(true);
		return SingleAct.builder()
				.name("chooseDescriptionItems")
				.text(action.getAnswers()[1]+ "? okey... Give me some description which you want to see when you will look in your bag.(Write)")
				.action(action)
				.build();
	}

	private SingleAct agreedItems() 
	{
		Items item = new Items();
		item.setName(action.getAnswers()[1]);
		item.setDescription(action.getAnswers()[2]);
		action.setButtons(new String[][] {{"Yeah, right"}});
		action.setObjectDnd(item);
		return SingleAct.builder()
				.name("checkItemCondition")
				.text(item.getDescription())
				.action(action)
				.build();
	}

}

class AmmunitionMenu extends Executor<Action> {

	public AmmunitionMenu(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		switch (action.condition()) {
		case 1:
			return chooseTypeAmmunition();
		case 2:
			return agreedAmmunition();
		case 3:
			return ItemFactory.finish(action, user.getCharactersPool().getActual());
		default:
		return null;
		}
	}

	private SingleAct chooseTypeAmmunition() {
		action.setButtons(ItemFactory.arrayToThreeColums(Ammunitions.values()));
		return SingleAct.builder()
				.name("chooseTypeAmmunition")
				.text("What ammunition is it?")
				.action(action)
				.build();
	}

	private SingleAct agreedAmmunition() {
		action.setButtons(new String[][] {{"Yeah, right"}});
		Ammunition ammunition = new Ammunition(targetAmmunition(action.getAnswers()[1]));
		action.setObjectDnd(ammunition);
		return SingleAct.builder()
				.name("checkCondition")
				.text(ammunition.getDescription())
				.action(action)
				.build();
	}

	private Ammunitions targetAmmunition(String name) {
		for (Ammunitions type : Ammunitions.values()) {
			if (type.toString().equals(name))
				return type;
		}
		return null;
	}

}

class WeaponMenu extends Executor<Action> {

	public WeaponMenu(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		switch (action.condition()) {
		case 1:
			return chooseTypeWeapon();
		case 2:
			return agreedWeapon();
		case 3:
			return ItemFactory.finish(action, user.getCharactersPool().getActual());
		default:
		return null;
		}
	}

	private SingleAct chooseTypeWeapon() {
		action.setButtons(ItemFactory.arrayToThreeColums(Weapons.values()));
		return SingleAct.builder()
				.name("ChooseTypeWeapon")
				.text("What weapon is it?")
				.action(action)
				.build();
	}

	private SingleAct agreedWeapon() {
		action.setButtons(new String[][] {{"Yeah, right"}});
		Weapon weapon = new Weapon(targetWeapon(action.getAnswers()[1]));
		action.setObjectDnd(weapon);
		return SingleAct.builder()
				.name("checkCondition")
				.text(weapon.getDescription())
				.action(action)
				.build();
	}

	private static Weapons targetWeapon(String name)
	{
		for(Weapons type: Weapons.values())
		{
			if(type.toString().equals(name)) return type;
		}
		return null;
	}

}

class ToolMenu extends Executor<Action> {

	public ToolMenu(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		switch (action.condition()) {
		case 1:
			return chooseTypeTool();
		case 2:
			return agreedTool();
		case 3:
			return ItemFactory.finish(action, user.getCharactersPool().getActual());
		default:
		return null;
		}
	}

	private SingleAct chooseTypeTool() {
		action.setButtons(ItemFactory.arrayToThreeColums(Tools.values()));
		return SingleAct.builder()
				.name("chooseTypeAmmunition")
				.text("What ammunition is it?")
				.action(action)
				.build();
	}

	private SingleAct agreedTool() {
		action.setButtons(new String[][] {{"Yeah, right"}});
		Tool ammunition = new Tool(targetAmmunition(action.getAnswers()[1]));
		action.setObjectDnd(ammunition);
		return SingleAct.builder()
				.name("checkCondition")
				.text(ammunition.getDescription())
				.action(action)
				.build();
	}

	private Tools targetAmmunition(String name) {
		for (Tools type : Tools.values()) {
			if (type.toString().equals(name))
				return type;
		}
		return null;
	}

}

class PackMenu extends Executor<Action> {

	public PackMenu(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		switch (action.condition()) {
		case 1:
			return chooseTypePack();
		case 2:
			return agreedPack();
		case 3:
			return ItemFactory.finish(action, user.getCharactersPool().getActual());
		default:
		return null;
		}
	}

	private SingleAct chooseTypePack() {
		action.setButtons(ItemFactory.arrayToThreeColums(Packs.values()));
		return SingleAct.builder()
				.name("chooseTypeAmmunition")
				.text("What ammunition is it?")
				.action(action)
				.build();
	}

	private SingleAct agreedPack() {
		action.setButtons(new String[][] {{"Yeah, right"}});
		Pack ammunition = new Pack(targetAmmunition(action.getAnswers()[1]));
		action.setObjectDnd(ammunition);
		return SingleAct.builder()
				.name("checkCondition")
				.text(ammunition.getDescription())
				.action(action)
				.build();
	}

	private Packs targetAmmunition(String name) {
		for (Packs type : Packs.values()) {
			if (type.toString().equals(name))
				return type;
		}
		return null;
	}

}

class ArmorMenu extends Executor<Action> {

	public ArmorMenu(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		switch (action.condition()) {
		case 1:
			return chooseTypeArmor();
		case 2:
			return agreedArmor();
		case 3:
			return ItemFactory.finish(action, user.getCharactersPool().getActual());
		default:
		return null;
		}
	}

	private SingleAct chooseTypeArmor() {
		action.setButtons(ItemFactory.arrayToThreeColums(Armors.values()));
		return SingleAct.builder()
				.name("chooseTypeAmmunition")
				.text("What ammunition is it?")
				.action(action)
				.build();
	}

	private SingleAct agreedArmor() {
		action.setButtons(new String[][] {{"Yeah, right"}});
		Armor ammunition = new Armor(targetAmmunition(action.getAnswers()[1]));
		action.setObjectDnd(ammunition);
		return SingleAct.builder()
				.name("checkCondition")
				.text(ammunition.getDescription())
				.action(action)
				.build();
	}

	private Armors targetAmmunition(String name) {
		for (Armors type : Armors.values()) {
			if (type.toString().equals(name))
				return type;
		}
		return null;
	}

}
