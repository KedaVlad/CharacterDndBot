package app.player.service.stage.event.factory;

import app.player.model.event.StageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.enums.Ammunitions;
import app.dnd.model.enums.Armors;
import app.dnd.model.enums.Packs;
import app.dnd.model.enums.Tools;
import app.dnd.model.enums.Weapons;
import app.dnd.model.stuffs.items.Ammunition;
import app.dnd.model.stuffs.items.Armor;
import app.dnd.model.stuffs.items.Items;
import app.dnd.model.stuffs.items.Pack;
import app.dnd.model.stuffs.items.Tool;
import app.dnd.model.stuffs.items.Weapon;
import app.dnd.service.DndFacade;
import app.dnd.util.ArrayToColumns;
import app.player.model.EventExecutor;
import app.player.model.act.Act;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.player.service.stage.Executor;
import app.bot.model.user.ActualHero;


@EventExecutor(Location.ITEMS_FACTORY)
public class ItemFactory implements Executor {

	@Autowired
	private ItemFactoryExecutor itemFactoryExecutor;


	@Override
	public Act execute(StageEvent event) {
		
		Action action = (Action) event.getTusk();
		if (action.condition() == 0) {
			return itemFactoryExecutor.menu();
		} else if(action.getAnswers()[0].equals(Button.ELSE.NAME)) {
			return switch (action.condition()) {
				case 1 -> itemFactoryExecutor.elseItemsName(action);
				case 2 -> itemFactoryExecutor.elseItemsDescription(action);
				case 3 -> itemFactoryExecutor.elseItemsCheckCondition(action);
				case 4 -> itemFactoryExecutor.endCreate(event.getUser().getActualHero(), action);
				default -> null;
			};
		} else {
			return switch (action.condition()) {
				case 1 -> itemFactoryExecutor.validItemChooseType(action);
				case 2 -> itemFactoryExecutor.validItemCondition(action);
				case 3 -> itemFactoryExecutor.endCreate(event.getUser().getActualHero(), action);
				default -> null;
			};
		}
	}

}

@Component
class ItemFactoryExecutor {

	@Autowired
	private DndFacade dndFacede;
	@Autowired
	private ArrayToColumns arrayToColumns;

	public Act menu() {

		return SingleAct.builder()
				.name("CreateItem")
				.stage(dndFacede.action().stuff().factoryMenu())
				.build();
	}

	public Act validItemChooseType(Action action) {
		if(action.getAnswers()[0].equals(Button.WEAPON.NAME)) {
			action.setButtons(arrayToColumns.rebuild(rebuildToString(Weapons.values()),3, String.class));
			action.setText("What weapon is it?");
		} else if(action.getAnswers()[0].equals(Button.AMNUNITION.NAME)) {
			action.setButtons(arrayToColumns.rebuild(rebuildToString(Ammunitions.values()),3, String.class));
			action.setText("What ammunition is it?");
		} else if(action.getAnswers()[0].equals(Button.TOOL.NAME)) {
			action.setButtons(arrayToColumns.rebuild(rebuildToString(Tools.values()),3, String.class));
			action.setText("What tool is it?");
		} else if(action.getAnswers()[0].equals(Button.PACK.NAME)) {
			action.setButtons(arrayToColumns.rebuild(rebuildToString(Packs.values()),3, String.class));
			action.setText("What pack is it?");
		} else if(action.getAnswers()[0].equals(Button.ARMOR.NAME)) {
			action.setButtons(arrayToColumns.rebuild(rebuildToString(Armors.values()),3, String.class));
			action.setText("What armor is it?");
		} 
		return SingleAct.builder()
				.name("validItemChooseType")
				.stage(action)
				.build();
	}

	public Act validItemCondition(Action action) {
		
		Items item = null;
		if(action.getAnswers()[0].equals(Button.WEAPON.NAME)) {
			item = new Weapon(targetAmmunition(Weapons.values(), action.getAnswers()[1]));
		} else if(action.getAnswers()[0].equals(Button.AMNUNITION.NAME)) {
			item = new Ammunition(targetAmmunition(Ammunitions.values(), action.getAnswers()[1]));
		} else if(action.getAnswers()[0].equals(Button.TOOL.NAME)) {
			item = new Tool(targetAmmunition(Tools.values(), action.getAnswers()[1]));
		} else if(action.getAnswers()[0].equals(Button.PACK.NAME)) {
			item = new Pack(targetAmmunition(Packs.values(), action.getAnswers()[1]));
		} else if(action.getAnswers()[0].equals(Button.ARMOR.NAME)) {
			item = new Armor(targetAmmunition(Armors.values(), action.getAnswers()[1]));
		} 
		
		action.setButtons(new String[][] {{"Yeah, right"}});
		action.setObjectDnd(item);
		action.setText(item.getDescription());
		return SingleAct.builder()
				.name("validItemCondition")
				.stage(action)
				.build();
	}
	

	public Act elseItemsName(Action action) {
		action.setText("How would you name this item?(Write)");
		return SingleAct.builder()
				.name("chooseNameItems")
				.mediator(true)
				.stage(action)
				.build();
	}

	public Act elseItemsDescription(Action action) {
		action.setText(action.getAnswers()[1]+ "? okey... Give me some description which you want to see when you will look in your bag.(Write)");
		return SingleAct.builder()
				.name("chooseDescriptionItems")
				.mediator(true)
				.stage(action)
				.build();
	}

	public Act elseItemsCheckCondition(Action action) {
		Items item = new Items();
		item.setName(action.getAnswers()[1]);
		item.setDescription(action.getAnswers()[2]);
		action.setButtons(new String[][] {{"Yeah, right"}});
		action.setObjectDnd(item);
		action.setText(item.getName() + "\n" + item.getDescription());
		return SingleAct.builder()
				.name("checkItemCondition")
				.stage(action)
				.build();
	}



	public Act endCreate(ActualHero hero, Action action) {
		dndFacede.hero().stuff().bag().addItem(hero, (Items)action.getObjectDnd());
		return ReturnAct.builder().target(Button.STUFF.NAME).call(Button.BAG.NAME).build();
	}

	private String[] rebuildToString(Object[] objects) {
		String[] answer = new String[objects.length];
		for(int i = 0; i < objects.length; i++) {
			answer[i] = objects[i].toString();
		}
		return answer;
	}
	
	private <T>T targetAmmunition(T[] arr, String name) {
		for (T type : arr) {
			if (type.toString().equals(name))
				return type;
		}
		return null;
	}
}

