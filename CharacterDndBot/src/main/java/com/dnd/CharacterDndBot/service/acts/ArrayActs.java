package com.dnd.CharacterDndBot.service.acts;

import com.dnd.CharacterDndBot.service.acts.actions.BaseAction;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ArrayActs extends ActiveAct { 
	
	private static final long serialVersionUID = 1L;
	private SingleAct[] pool;
	private long[] keys;

	public static ArrayActsBuilder builder() {
		return new ArrayActsBuilder();
	}

	public SingleAct getTarget(long key) {
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] == key) {
				return pool[i];
			}
		}
		return null;
	}

	@Override
	public boolean hasAction() {
		return pool[0].getAction() != null;
	}

	@Override
	public boolean hasMediator() {
		return false;
	}

	@Override
	public boolean hasReply(String string) {
		return pool[0].getAction() != null && pool[0].getAction().replyContain(string);
	}

	@Override
	public boolean hasCloud() {
		return false;
	}

	@Override
	public BaseAction getAction() {
		return null;
	}
}