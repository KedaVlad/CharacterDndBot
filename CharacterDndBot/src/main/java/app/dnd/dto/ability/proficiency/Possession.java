package app.dnd.dto.ability.proficiency;
 
import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.dto.ObjectDnd;
import app.dnd.dto.ability.proficiency.Proficiencies.Proficiency;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("possession")
@Data
@EqualsAndHashCode(callSuper=false)
public class Possession extends ObjectDnd {

	private Proficiency prof;
	private String name;
	
	public Possession() {}

	public Possession(String name) {
		this.name = name;
		this.prof = Proficiency.BASE;
	}
	
	public Possession(String name, Proficiency prof) {
		this.name = name;
		this.prof = prof;
	}
	
	public String toString() {
		String answer = name;
		if(prof != null) answer += " (" + prof.toString() + ")";
		return answer;
	}
}
