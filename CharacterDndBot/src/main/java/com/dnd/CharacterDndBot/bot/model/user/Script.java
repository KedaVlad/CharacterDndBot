package com.dnd.CharacterDndBot.bot.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.dnd.CharacterDndBot.bot.model.act.ActiveAct;
import com.dnd.CharacterDndBot.bot.model.act.SingleAct;

import lombok.Data;

@Data
public class Script implements Serializable { 
	
	private static final long serialVersionUID = 1L;
	private final LinkedList<ActiveAct> mainTree;
	private final List<Integer> trash;

	Script(long id) {
		mainTree = new LinkedList<>();
		trash = new ArrayList<>();
		mainTree.add(SingleAct.builder().name(id + "").build());
	}

	public boolean targeting(String target) {
		for (int i = 0; i < mainTree.size(); i++) {
			if (mainTree.get(i).getName().equals(target)) {
				backTo(i);
				return true;
			}
		}
		return false;
	}

	public void backTo(int target) {
		
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