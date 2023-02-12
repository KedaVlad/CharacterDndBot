package com.dnd.CharacterDndBot.service.acts;

import lombok.Data;

@Data
public class ReturnAct implements Act {
	
	private static final long serialVersionUID = 1L;
	private String target;
	private String call;
	private ActiveAct act;

	ReturnAct() {}

	public static ReturnActBuilder builder() {
		return new ReturnActBuilder();
	}
}
