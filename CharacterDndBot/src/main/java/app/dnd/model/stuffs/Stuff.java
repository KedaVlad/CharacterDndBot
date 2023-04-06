package app.dnd.model.stuffs;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import app.dnd.model.stuffs.items.Armor;
import app.dnd.model.stuffs.items.Items;
import app.dnd.util.ButtonName;
import lombok.Data;

@Data
@Document(collection = "stuff")
public class Stuff { 
	
	@Id
	private Long id;
	private List<Items> insideBag;
	private List<String> status;
	private List<Items> prepeared;
	private Armor[] weared = new Armor[2];
	
	@Override
	public String getInformation() {
		return  ButtonName.BAG_B + " ("+ insideBag.size()+")"
				+"\n" + ButtonName.CARRYING_STUFF_B +" (" + prepeared.size()+")";
	}
	
	public static Stuff build(Long id) {
		Stuff stuff = new Stuff();
		stuff.id = id;
		stuff.insideBag = new ArrayList<>();
		stuff.status = new ArrayList<>();
		stuff.prepeared = new ArrayList<>();
		stuff.weared = new Armor[2];
		return stuff;
	}

}
