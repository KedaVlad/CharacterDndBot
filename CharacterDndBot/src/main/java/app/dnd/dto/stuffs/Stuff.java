package app.dnd.dto.stuffs;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import app.dnd.dto.Informator;
import app.dnd.dto.stuffs.items.Armor;
import app.dnd.dto.stuffs.items.Items;
import lombok.Data;

@Data
public class Stuff implements Informator, Serializable { 
	
	private static final long serialVersionUID = 1L;
	private Wallet wallet = new Wallet();
	private List<Items> insideBag = new ArrayList<>();
	private List<String> status = new ArrayList<>();
	private List<Items> prepeared = new ArrayList<>();
	private Armor[] weared = new Armor[2];

}
