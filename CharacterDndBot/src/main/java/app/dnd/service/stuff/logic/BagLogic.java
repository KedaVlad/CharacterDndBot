package app.dnd.service.stuff.logic;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.enums.ClassArmor;
import app.dnd.model.stuffs.ItemInHand;
import app.dnd.model.stuffs.Stuff;
import app.dnd.model.stuffs.items.Ammunition;
import app.dnd.model.stuffs.items.Armor;
import app.dnd.model.stuffs.items.Items;
import app.dnd.service.stuff.StuffService;
import app.player.model.enums.Button;
import app.user.model.ActualHero;

public interface BagLogic {

	String bagInfo(ActualHero actualHero);

	List<Items> getItemsIncideBag(ActualHero actualHero);
	
	void topUpAmmunition(ActualHero actualHero, Ammunition ammo, String valueInString);

	void throwItem(ActualHero actualHero, Items target);

	void wear(ActualHero actualHero, Armor target);

	void prepear(ActualHero actualHero, Items target);

	List<ItemInHand> getItemsInHand(ActualHero actualHero);

	void fromHandToBag(ActualHero actualHero, ItemInHand objectDnd);

	void addItem(ActualHero hero, Items objectDnd);
}


@Component
class BagFacade implements BagLogic {

	@Autowired
	private StuffService stuffService;
	
	@Override
	public String bagInfo(ActualHero actualHero) {
		if(stuffService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName()).getInsideBag().isEmpty()) {
			return "Warning: Your "+ Button.BAG.NAME +" is currently empty. You may want to consider adding an item to assist you on your journey. Would you like to "+ Button.ADD.NAME +" an item now?";		
		} else {
			return Button.BAG.NAME +  "\r\n"
					+ "\r\n" 
					+ Button.ADD.NAME +": Add the element to the backpack\r\n"		
					+ Button.THROW_OUT.NAME +": delete the element from the backpack\r\n"
					+ Button.USE.NAME +"Use the element: Use the item that can be used by one -time (potion, scroll)\r\n"
					+ Button.PREPEAR.NAME + "|" + Button.WEAR.NAME +": Equip stuff from backpack, such as armor or weapon\r\n"
					+ "\r\n"
					+ "Please note that the availability of certain interactions may depend on the element and its properties. In addition, remember, some objects cannot be transferred to a backpack from their size.";
		}
		
	}

	@Override
	public List<Items> getItemsIncideBag(ActualHero actualHero) {
		return stuffService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName()).getInsideBag();
	}
	
	@Override
	public List<ItemInHand> getItemsInHand(ActualHero actualHero) {
		return stuffService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName()).getItemsInHand();
	}
	
	@Override
	public void topUpAmmunition(ActualHero actualHero, Ammunition ammo, String valueInString) {
		int value = 0;
		Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
		Matcher matcher = pat.matcher(valueInString);
		while (matcher.find()) {
			value = ((Integer) Integer.parseInt(matcher.group()));
		}
		if (valueInString.contains("-")) value = value * -1;
		
		Stuff stuff = stuffService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		
		for(Items item: stuff.getInsideBag()) {
			if(item.equals(ammo)) {
				((Ammunition) item).addValue(value);
				stuffService.save(stuff);
				return;
			}
		}
	}
	
	@Override
	public void throwItem(ActualHero actualHero, Items target) {
		Stuff stuff = stuffService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		stuff.getInsideBag().remove(target);
		stuffService.save(stuff);
	}

	@Override
	public void wear(ActualHero actualHero, Armor armor) {
		Stuff stuff = stuffService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		wearArmor(stuff, armor);
		stuffService.save(stuff);
	}
	
	@Override
	public void prepear(ActualHero actualHero, Items target) {
		Stuff stuff = stuffService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		if(target instanceof Armor) {
			wearArmor(stuff, (Armor) target);
		} else {
		stuff.getInsideBag().remove(target);
		stuff.getItemsInHand().add(ItemInHand.build(target));
		}
		stuffService.save(stuff);
	}
	
	@Override
	public void fromHandToBag(ActualHero actualHero, ItemInHand itemInHand) {
		
		Stuff stuff = stuffService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		if(itemInHand.getItem() instanceof Armor) {
			removeArmor(stuff, (Armor) itemInHand.getItem());
		} else {
			stuff.getItemsInHand().remove(itemInHand);
			stuff.getInsideBag().add(itemInHand.getItem());
		}
		stuffService.save(stuff);
	}
	
	private void wearArmor(Stuff stuff, Armor armor) {
		stuff.getInsideBag().remove(armor);
		stuff.getItemsInHand().add(ItemInHand.build(armor));
		int position = 0;
		if (armor.getType().getClazz().equals(ClassArmor.SHIELD)) {
			position = 1;
		}
		if (stuff.getWeared()[position] != null) {
			removeArmor(stuff, stuff.getWeared()[position]);
		}
		stuff.getWeared()[position] = armor;
	}
	
	private void removeArmor(Stuff stuff, Armor armor) {
		
		int position = 0;
		if (armor.getType().getClazz().equals(ClassArmor.SHIELD)) {
			position = 1;
		}
		for(ItemInHand item: stuff.getItemsInHand()) {
			if(item.getItem().equals(armor)) {
				stuff.getInsideBag().add(armor);
				stuff.getItemsInHand().remove(item);
				break;
			}
		}
		stuff.getWeared()[position] = null;
		
	}

	@Override
	public void addItem(ActualHero actualHero, Items item) {
		Stuff stuff = stuffService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		stuff.getInsideBag().add(item);
		stuffService.save(stuff);
	}
	
}
