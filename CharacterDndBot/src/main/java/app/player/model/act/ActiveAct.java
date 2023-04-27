package app.player.model.act;
 
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import app.bot.model.message.MessageCore;
import app.player.model.Stage;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CloudAct.class, name = "cloud_act"),
        @JsonSubTypes.Type(value = TreeAct.class, name = "tree_act")
})
public abstract class ActiveAct implements Act, MessageCore {
 
	private String name;
	private List<Integer> actCircle;
	
	
	public abstract Stage continueAct(String string);
	
	@Override
	public void catchId(Integer act) {
		this.actCircle.add(act);
	}
	
	public List<Integer> end() {
		List<Integer> end = new ArrayList<>(actCircle);
		actCircle.clear();
		return end;
	}


	
}
