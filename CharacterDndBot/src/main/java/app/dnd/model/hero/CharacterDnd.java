package app.dnd.model.hero;
 
import java.util.ArrayList;
import java.util.List;

import app.player.model.act.SingleAct;
import lombok.Data;

@Data
public class CharacterDnd { 
	 
	private String name;
	private boolean ready;
	private List<SingleAct> clouds;

	public CharacterDnd(String name) {
		this.name = name;
		this.clouds = new ArrayList<>();
	}
}
