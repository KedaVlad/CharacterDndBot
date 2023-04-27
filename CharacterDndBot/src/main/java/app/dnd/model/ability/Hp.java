package app.dnd.model.ability;


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
	private String mongoId;
	private Long userId;
	private String ownerName;
	private int max = 0;
	private int now = 0;
	private int timeHp = 0;
	private boolean knocked = false;
	private boolean dead = false;
	private int hpBonus  = 0;

	@Override
	public void refresh(Time time) {
		if (time == Time.LONG) {
			now = max;
		}
	}
	
	public void grow(int value) {
		max += value;
		now = max;
	}
	
	public void heal(int value) {
		now += value;
		if (now > 0 && knocked)
			knocked = false;
		if (now > max) {
			now = max;
		}
	}
	
	public void damaged(int value) {

		if (timeHp > 0) {
			timeHp -= value;
			if (timeHp < 0) {
				now += timeHp;
				timeHp = 0;
			}
		} else {
			now -= value;
		}
		if (now < 0) knocked = true;
		if (now < max * -1) dead = true;
	}
	
	public void addTimeHp(int value) {
		timeHp += value;
	}
	
	public String shortInfo() {
		String answer = "HP: " + now;
		if (timeHp > 0) {
			answer += "[+" + timeHp + "]";
		} 
		answer += "|" + max;
		if (dead) {
			answer += " (DEAD)";
		} else if (knocked) {
			answer += " (knocked)";
		}
		return answer;
	}
	
	public static Hp build(Long id, String ownerName) {
		Hp hp = new Hp();
		hp.userId = id;
		hp.ownerName = ownerName;
		return hp;
	}
}
