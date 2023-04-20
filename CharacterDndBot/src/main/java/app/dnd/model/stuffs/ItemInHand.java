package app.dnd.model.stuffs;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.ObjectDnd;
import app.dnd.model.stuffs.items.Items;
import lombok.Data;

@JsonTypeName("item_in_hand")
@Data
public class ItemInHand implements ObjectDnd {

	private Items item;
	
	public static ItemInHand build(Items item) {
		ItemInHand itemInHand = new ItemInHand();
		itemInHand.item = item;
		return itemInHand;
	}
	
}
