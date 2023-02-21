package com.dnd.CharacterDndBot.bot.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.CharacterDndBot.bot.model.act.ActiveAct;
import com.dnd.CharacterDndBot.bot.model.act.SingleAct;

import lombok.Data;
@Data
public class Clouds implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final List<Integer> trash;
	private List<SingleAct> cloudsTarget;
	private List<SingleAct> cloudsWorked;

	Clouds(List<Integer> trash) {
		this.trash = trash;
		this.cloudsTarget = new ArrayList<>();
		this.cloudsWorked = new ArrayList<>();
	}

	List<ActiveAct> compleatClouds() {
		List<ActiveAct> targetClouds = new ArrayList<>();
		targetClouds.addAll(cloudsTarget);
		cloudsWorked.addAll(cloudsTarget);
		cloudsTarget.clear();
		return targetClouds;
	}

	void initialize(List<SingleAct> list) {
		this.cloudsTarget.addAll(cloudsWorked);
		for (ActiveAct act : cloudsWorked) {
			this.trash.addAll(act.end());
		}
		this.cloudsWorked.clear();
		this.cloudsTarget = list;
	}
}
