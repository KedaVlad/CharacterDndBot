package app.player.model.act;

import app.dnd.model.actions.Action;

public class ReturnActBuilder {
	
	private String target;
	private String call;
	private ActiveAct act;
	private Action action;

	public ReturnActBuilder target(String target) {
		this.target = target;
		return this;
	}

	public ReturnActBuilder call(String call) {
		this.call = call;
		return this;
	}

	public ReturnActBuilder act(ActiveAct act) {
		this.act = act;
		this.action = null;
		return this;
	}
	
	public ReturnActBuilder action(Action action) {
		this.action = action;
		this.act = null;
		return this;
	}

	public ReturnAct build() {
		ReturnAct answer = new ReturnAct();
		answer.setTarget(target);
		if (call == null && action == null) {
			answer.setAct(act);
		} else if (call == null && act == null){
			answer.setAction(action);
		} else {
			answer.setCall(call);
		}
		return answer;
	}
}
