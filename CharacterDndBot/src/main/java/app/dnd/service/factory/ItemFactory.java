package app.dnd.service.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.bot.model.act.Act;
import app.bot.model.act.ReturnAct;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.user.User;
import app.dnd.dto.stuffs.items.Ammunition;
import app.dnd.dto.stuffs.items.Armor;
import app.dnd.dto.stuffs.items.Items;
import app.dnd.dto.stuffs.items.Pack;
import app.dnd.dto.stuffs.items.Tool;
import app.dnd.dto.stuffs.items.Weapon;
import app.dnd.dto.stuffs.items.Ammunition.Ammunitions;
import app.dnd.dto.stuffs.items.Armor.Armors;
import app.dnd.dto.stuffs.items.Pack.Packs;
import app.dnd.dto.stuffs.items.Tool.Tools;
import app.dnd.dto.stuffs.items.Weapon.Weapons;
import app.dnd.service.Executor;
import app.dnd.service.Location;
import app.dnd.util.ArrayToThreeColums;

@Service
public class ItemFactory implements Executor<Action> {

	@Autowired
	private ItemStartCreate itemStartCreate;
	@Autowired
	private ElseItemsExecutor elseItemsExecutor;
	@Autowired
	private AmmunitionExecutor ammunitionExecutor;
	@Autowired
	private WeaponExecutor weaponExecutor;
	@Autowired
	private ToolExecutor toolExecutor;
	@Autowired
	private PackExecutor packExecutor;
	@Autowired
	private ArmorExecutor armorExecutor;
	
	
	@Override
	public Act executeFor(Action action, User user) {
		if (action.condition() == 0) {
			return itemStartCreate.executeFor(action, user);
		} else {
			String target = action.getAnswers()[0];
			switch(target)
			{
			case WEAPON_B:
				return weaponExecutor.executeFor(action, user);
			case AMNUNITION_B:
				return ammunitionExecutor.executeFor(action, user);
			case TOOL_B:
				return toolExecutor.executeFor(action, user);
			case PACK_B:
				return packExecutor.executeFor(action, user);
			case ARMOR_B:
				return armorExecutor.executeFor(action, user);
			case ELSE_B:
				return elseItemsExecutor.executeFor(action, user);
			default:
				return null;
			}
		}
	}
}

@Component
class ItemStartCreate implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
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
}

@Component
class ItemFinishCreate implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
		user.getCharactersPool().getActual().getStuff().getInsideBag().add((Items)action.getObjectDnd());
		return ReturnAct.builder().target(STUFF_B).call(BAG_B).build();
	}
}

@Component
class ElseItemsExecutor implements Executor<Action> {
	
	@Autowired
	private ElseItemsName elseItemsName;
	@Autowired
	private ElseItemsDescription elseItemsDescription;
	@Autowired
	private ElseItemsCheckCondition elseItemsCheckCondition;
	@Autowired
	private ItemFinishCreate itemFinishCreate;
	
	@Override
	public Act executeFor(Action action, User user) {
		switch (action.getAnswers().length) {
		case 1:
			return	elseItemsName.executeFor(action, user);
		case 2:
			return  elseItemsDescription.executeFor(action, user);
		case 3:
			return  elseItemsCheckCondition.executeFor(action, user);
		case 4:
			return itemFinishCreate.executeFor(action, user);
		}
		return null;
	}
}

@Component
class ElseItemsName implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {

		action.setMediator(true);
		return SingleAct.builder()
				.name("chooseNameItems")
				.text("How would you name this item?(Write)")
				.action(action)
				.build();
	}
}

@Component
class ElseItemsDescription implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {

		action.setMediator(true);
		return SingleAct.builder()
				.name("chooseDescriptionItems")
				.text(action.getAnswers()[1]+ "? okey... Give me some description which you want to see when you will look in your bag.(Write)")
				.action(action)
				.build();
	}
}

@Component
class ElseItemsCheckCondition implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {

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

@Component
class AmmunitionExecutor implements Executor<Action> {

	@Autowired
	private ItemFinishCreate itemFinishCreate;
	@Autowired
	private AmmunitionChooseType ammunitionChooseType;
	@Autowired
	private AmmunitionCheckCondition ammunitionCheckCondition;
	
	@Override
	public Act executeFor(Action action, User user) {

		switch (action.condition()) {
		case 1:
			return ammunitionChooseType.executeFor(action, user);
		case 2:
			return ammunitionCheckCondition.executeFor(action, user);
		case 3:
			return itemFinishCreate.executeFor(action, user);
		default:
			return null;
		}
	}
}

@Component
class AmmunitionChooseType implements Executor<Action> {

	@Autowired
	private ArrayToThreeColums arrayToThreeColums;
	
	@Override
	public Act executeFor(Action action, User user) {

		action.setButtons(arrayToThreeColums.rebuild(Ammunitions.values()));
		return SingleAct.builder()
				.name("chooseTypeAmmunition")
				.text("What ammunition is it?")
				.action(action)
				.build();
	}
}

@Component
class AmmunitionCheckCondition implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
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

@Component
class WeaponExecutor implements Executor<Action> {

	@Autowired
	private ItemFinishCreate itemFinishCreate;
	@Autowired
	private WeaponChooseType weaponChooseType;
	@Autowired
	private WeaponCheckCondition weaponCheckCondition;
	
	@Override
	public Act executeFor(Action action, User user) { 

		switch (action.condition()) {
		case 1:
			return weaponChooseType.executeFor(action, user);
		case 2:
			return weaponCheckCondition.executeFor(action, user);
		case 3:
			return itemFinishCreate.executeFor(action, user);
		default:
			return null;
		}
	}
}

@Component
class WeaponChooseType implements Executor<Action> {

	@Autowired
	private ArrayToThreeColums arrayToThreeColums;
	
	@Override
	public Act executeFor(Action action, User user) {

		action.setButtons(arrayToThreeColums.rebuild(Weapons.values()));
		return SingleAct.builder()
				.name("ChooseTypeWeapon")
				.text("What weapon is it?")
				.action(action)
				.build();
	}
}

@Component
class WeaponCheckCondition implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {

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


@Component
class ToolExecutor implements Executor<Action> {

	@Autowired
	private ItemFinishCreate itemFinishCreate;
	@Autowired
	private ToolChooseType toolChooseType;
	@Autowired
	private ToolCheckCondition toolCheckCondition;
	
	@Override
	public Act executeFor(Action action, User user) {
		switch (action.condition()) {
		case 1:
			return toolChooseType.executeFor(action, user);
		case 2:
			return toolCheckCondition.executeFor(action, user);
		case 3:
			return itemFinishCreate.executeFor(action, user);
		default:
			return null;
		}
	}
}

@Component
class ToolChooseType implements Executor<Action> {

	@Autowired
	private ArrayToThreeColums arrayToThreeColums;
	
	@Override
	public Act executeFor(Action action, User user) {

		action.setButtons(arrayToThreeColums.rebuild(Tools.values()));
		return SingleAct.builder()
				.name("chooseTypeTool")
				.text("What tool is it?")
				.action(action)
				.build();
	}
}

@Component
class ToolCheckCondition implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {

		action.setButtons(new String[][] {{"Yeah, right"}});
		Tool ammunition = new Tool(targetTool(action.getAnswers()[1]));
		action.setObjectDnd(ammunition);
		return SingleAct.builder()
				.name("checkCondition")
				.text(ammunition.getDescription())
				.action(action)
				.build();
	}

	private Tools targetTool(String name) {
		for (Tools type : Tools.values()) {
			if (type.toString().equals(name))
				return type;
		}
		return null;
	}

}

@Component
class PackExecutor implements Executor<Action> {

	@Autowired
	private ItemFinishCreate itemFinishCreate;
	@Autowired
	private PackChooseType packChooseType;
	@Autowired
	private PackCheckCondition packCheckCondition;
	
	@Override
	public Act executeFor(Action action, User user) {
		switch (action.condition()) {
		case 1:
			return packChooseType.executeFor(action, user);
		case 2:
			return packCheckCondition.executeFor(action, user);
		case 3:
			return itemFinishCreate.executeFor(action, user);
		default:
			return null;
		}
	}
}

@Component
class PackChooseType implements Executor<Action> {

	@Autowired
	private ArrayToThreeColums arrayToThreeColums;
	
	@Override
	public Act executeFor(Action action, User user) {

		action.setButtons(arrayToThreeColums.rebuild(Packs.values()));
		return SingleAct.builder()
				.name("chooseTypePack")
				.text("What pack is it?")
				.action(action)
				.build();
	}
}

@Component
class PackCheckCondition implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {

		action.setButtons(new String[][] {{"Yeah, right"}});
		Pack ammunition = new Pack(targetPack(action.getAnswers()[1]));
		action.setObjectDnd(ammunition);
		return SingleAct.builder()
				.name("checkCondition")
				.text(ammunition.getDescription())
				.action(action)
				.build();
	}

	private Packs targetPack(String name) {
		for (Packs type : Packs.values()) {
			if (type.toString().equals(name))
				return type;
		}
		return null;
	}

}

@Component
class ArmorExecutor implements Executor<Action> {

	@Autowired
	private ItemFinishCreate itemFinishCreate;
	@Autowired
	private ArmorChooseType armorChooseType;
	@Autowired
	private ArmorCheckCondition armorCheckCondition;
	
	@Override
	public Act executeFor(Action action, User user) {

		switch (action.condition()) {
		case 1:
			return armorChooseType.executeFor(action, user);
		case 2:
			return armorCheckCondition.executeFor(action, user);
		case 3:
			return itemFinishCreate.executeFor(action, user);
		default:
			return null;
		}
	}

}

@Component
class ArmorChooseType implements Executor<Action> {

	@Autowired
	private ArrayToThreeColums arrayToThreeColums;
	
	@Override
	public Act executeFor(Action action, User user) {

		action.setButtons(arrayToThreeColums.rebuild(Armors.values()));
		return SingleAct.builder()
				.name("chooseTypeArmor")
				.text("What armor is it?")
				.action(action)
				.build();
	}


}

@Component
class ArmorCheckCondition implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {

		action.setButtons(new String[][] {{"Yeah, right"}});
		Armor ammunition = new Armor(targetArmor(action.getAnswers()[1]));
		action.setObjectDnd(ammunition);
		return SingleAct.builder()
				.name("checkCondition")
				.text(ammunition.getDescription())
				.action(action)
				.build();
	}

	private Armors targetArmor(String name) {
		for (Armors type : Armors.values()) {
			if (type.toString().equals(name))
				return type;
		}
		return null;
	}

}
