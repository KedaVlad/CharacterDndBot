package app.player.model.act;

import app.dnd.model.actions.Action;
import lombok.Data;

@Data
public class ReturnAct implements Act {
	
	private String target;
	private String call;
	private ActiveAct act;
	private Action action;

	ReturnAct() {}

	public static ReturnActBuilder builder() {
		return new ReturnActBuilder();
	}
}
