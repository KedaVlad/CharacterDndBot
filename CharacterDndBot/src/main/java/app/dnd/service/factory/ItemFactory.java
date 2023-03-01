package app.dnd.service.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.bot.model.ActualHero;
import app.bot.model.act.Act;
import app.bot.model.act.ReturnAct;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.user.User;
import app.bot.service.ActualHeroService;
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
import app.dnd.util.ArrayToColumns;

@Service
public class ItemFactory implements Executor<Action> {

	@Autowired
	private ItemStartCreate itemStartCreate;
	@Autowired
	private ElseItemsExecutor elseItemsExecutor;
	@Autowired
	private ValidItemExecutor validItemExecutor;
	
	@Override
	public Act executeFor(Action action, User user) {
		if (action.condition() == 0) {
			return itemStartCreate.executeFor(action, user);
		} else {
			
			if(action.getAnswers()[0].equals(ELSE_B)) {
				return elseItemsExecutor.executeFor(action, user);
			} else {
				return validItemExecutor.executeFor(action, user);
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

	@Autowired
	private ActualHeroService actualHeroService;
	
	@Override
	public Act executeFor(Action action, User user) {
		ActualHero actualHero = actualHeroService.getById(user.getId());
		actualHero.getCharacter().getStuff().getInsideBag().add((Items)action.getObjectDnd());
		actualHeroService.save(actualHero);
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
class ValidItemExecutor implements Executor<Action> {

	@Autowired
	private ValidItemChooseType validItemChooseType;
	@Autowired
	private ValidItemCondition validItemCondition;
	@Autowired
	private ItemFinishCreate itemFinishCreate;
	
	@Override
	public Act executeFor(Action action, User user) {

		switch (action.condition()) {
		case 1:
			return validItemChooseType.executeFor(action, user);
		case 2:
			return validItemCondition.executeFor(action, user);
		case 3:
			return itemFinishCreate.executeFor(action, user);
		default:
			return null;
		}
	}
}

@Component
class ValidItemChooseType implements Executor<Action> {

	@Autowired
	private ArrayToColumns arrayToColumns;
	
	@Override
	public Act executeFor(Action action, User user) {
		
		switch(action.getAnswers()[0]) {
		case WEAPON_B:
			action.setButtons(arrayToColumns.rebuild(rebuildToString(Weapons.values()),3, String.class));
			break;
		case AMNUNITION_B:
			action.setButtons(arrayToColumns.rebuild(rebuildToString(Ammunitions.values()),3, String.class));
			break;
		case TOOL_B:
			action.setButtons(arrayToColumns.rebuild(rebuildToString(Tools.values()),3, String.class));
			break;
		case PACK_B:
			action.setButtons(arrayToColumns.rebuild(rebuildToString(Packs.values()),3, String.class));
			break;
		case ARMOR_B:
			action.setButtons(arrayToColumns.rebuild(rebuildToString(Armors.values()),3, String.class));
			break;
		}

		return SingleAct.builder()
				.name("chooseTypeAmmunition")
				.text("What ammunition is it?")
				.action(action)
				.build();
	}
	
	private String[] rebuildToString(Object[] objects) {
		String[] answer = new String[objects.length];
		for(int i = 0; i < objects.length; i++) {
			answer[i] = objects[i].toString();
		}
		return answer;
	}
}

@Component
class ValidItemCondition implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
		
		Items item = null;
		
		switch(action.getAnswers()[0]) {
		case WEAPON_B:
			item = new Weapon(targetAmmunition(Weapons.values(), action.getAnswers()[1]));
			break;
		case AMNUNITION_B:
			item = new Ammunition(targetAmmunition(Ammunitions.values(), action.getAnswers()[1]));
			break;
		case TOOL_B:
			item = new Tool(targetAmmunition(Tools.values(), action.getAnswers()[1]));
			break;
		case PACK_B:
			item = new Pack(targetAmmunition(Packs.values(), action.getAnswers()[1]));
			break;
		case ARMOR_B:
			item = new Armor(targetAmmunition(Armors.values(), action.getAnswers()[1]));
			break;
		}
		
		
		action.setButtons(new String[][] {{"Yeah, right"}});
		action.setObjectDnd(item);
		return SingleAct.builder()
				.name("checkCondition")
				.text(item.getDescription())
				.action(action)
				.build();
	}

	private <T>T targetAmmunition(T[] arr, String name) {
		for (T type : arr) {
			if (type.toString().equals(name))
				return type;
		}
		return null;
	}

}
