package app.dnd.model.characteristics;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import app.dnd.model.ObjectDnd;
import app.dnd.model.Refreshable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Document(collection = "hp")
public class Hp implements Refreshable, ObjectDnd {

	@Id
	private Long id;
	private int max = 0;
	private int now = 0;
	private int timeHp = 0;
	private boolean cknoked = false;
	private boolean dead = false;
	private int hpBonus  = 0;

	@Override
	public void refresh(Time time) {
		if (time == Time.LONG) {
			now = max;
		}
	}

	@Override
	public String getInformation() {
		String answer = "HP: " + now;
		if (timeHp > 0) {
			answer += "[+" + timeHp + "]";
		} 
		answer += "|" + max;
		if (dead) {
			answer += " (DEAD)";
		} else if (cknoked) {
			answer += " (cknoked)";
		}
		return answer;
	}
	
	public static Hp build(Long id) {
		Hp hp = new Hp();
		hp.id = id;
		return hp;
	}
}
