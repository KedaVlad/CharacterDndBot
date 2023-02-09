package com.dnd.CharacterDndBot.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.dnd.CharacterDndBot.service.acts.ActiveAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;

import lombok.Data;

@Data
public class User {
	
	private final long id;
	private final CharactersPool charactersPool;
	private final Script script;

	User(long id) {
		this.id = id;
		script = new Script(id);
		charactersPool = new CharactersPool(script.getTrash());
	}

	public ReadyToWork makeWork(ActiveAct act) {
		List<ActiveAct> toWork = new ArrayList<>();
		if (act != null) toWork.add(act);
		if (charactersPool.hasCurrentCloud()) {
			toWork.addAll(charactersPool.getClouds().compleatClouds());
		}
		return new ReadyToWork(id, toWork, script.trashThrowOut());
	}
}

@Data
class Script { 
	
	private final LinkedList<ActiveAct> mainTree;
	private final List<Integer> trash;

	Script(long id) {
		mainTree = new LinkedList<>();
		trash = new ArrayList<>();
		mainTree.add(SingleAct.builder().name(id + "").build());
	}

	boolean targeting(String target) {
		for (int i = 0; i < mainTree.size(); i++) {
			if (mainTree.get(i).getName().equals(target)) {
				backTo(i);
				return true;
			}
		}
		return false;
	}

	void backTo(int target) {
		for (int j = mainTree.size() - 1; j > target; j--) {
			trash.addAll(mainTree.getLast().getActCircle());
			mainTree.removeLast();
		}
	}

	List<Integer> trashThrowOut() {
		List<Integer> throwed = new ArrayList<>();
		throwed.addAll(trash);
		trash.clear();
		return throwed;
	}

}

@Data
class Clouds {
	 
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

@Data
class ReadyToWork { 
	
	private List<ActiveAct> readyToSend;
	private List<Integer> trash;
	private final long id;

	ReadyToWork(long id, List<ActiveAct> readyToSend, List<Integer> trash) {
		this.id = id;
		this.readyToSend = readyToSend;
		this.trash = trash;
	}

}
