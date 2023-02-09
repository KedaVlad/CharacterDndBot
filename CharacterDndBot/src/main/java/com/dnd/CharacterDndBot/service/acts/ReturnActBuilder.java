package com.dnd.CharacterDndBot.service.acts;

public class ReturnActBuilder {
	
	private String target;
	private String call;
	private ActiveAct act;

	public ReturnActBuilder target(String target) {
		this.target = target;
		return this;
	}

	public ReturnActBuilder call(String call) {
		this.call = call;
		if (target == null)
			this.target = call;
		return this;
	}

	public ReturnActBuilder act(ActiveAct act) {
		this.act = act;
		return this;
	}

	public ReturnAct build() {
		ReturnAct answer = new ReturnAct();
		answer.setTarget(target);
		if (call == null) {
			answer.setAct(act);
		} else {
			answer.setCall(call);
		}
		return answer;
	}
}
