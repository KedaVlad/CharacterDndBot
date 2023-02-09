package com.dnd.CharacterDndBot.service.acts;

import java.util.ArrayList;

public class ArrayActsBuilder extends ActiveActBuilder<ArrayActsBuilder> {
	
	private SingleAct[] pool;

	ArrayActsBuilder() {}

	public static ArrayActsBuilder builder() {
		return new ArrayActsBuilder();
	}

	public ArrayActsBuilder pool(SingleAct... actions) {
		this.pool = actions;
		return this;
	}

	public ArrayActs build() {
		ArrayActs answer = new ArrayActs();
		answer.setName(this.name);
		answer.setPool(this.pool);
		long[] keys = new long[this.pool.length];
		long baseKey = 500000000;
		for (SingleAct act : this.pool) {
			act.setName(name);
		}
		for (int i = 0; i < this.pool.length; i++) {
			keys[i] = baseKey;
			baseKey++;
		}
		answer.setKeys(keys);
		answer.setActCircle(new ArrayList<>());
		return answer;
	}

	@Override
	protected ArrayActsBuilder self() {
		return this;
	}
}
