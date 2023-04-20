package app.dnd.model.stuffs;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import app.dnd.model.stuffs.items.Armor;
import app.dnd.model.stuffs.items.Items;
import lombok.Data;

@Data
@Document(collection = "stuff")
public class Stuff { 
	
	@Id
	private Long id;
	private String ownerName;
	private List<Items> insideBag;
	private List<String> status;
	private List<ItemInHand> itemsInHand;
	private Armor[] weared = new Armor[2];
	
	public static Stuff build(Long id, String ownerName) {
		Stuff stuff = new Stuff();
		stuff.id = id;
		stuff.ownerName = ownerName;
		stuff.insideBag = new ArrayList<>();
		stuff.status = new ArrayList<>();
		stuff.itemsInHand = new ArrayList<>();
		stuff.weared = new Armor[2];
		return stuff;
	}

}
