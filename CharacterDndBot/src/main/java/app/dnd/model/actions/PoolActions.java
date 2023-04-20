package app.dnd.model.actions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonTypeName("pool_action")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public class PoolActions extends BaseAction {

	private SingleAction[][] pool;

	PoolActions() {
	}

	public static PoolActionBuilder builder() {
		return new PoolActionBuilder();
	}


	@Override
	public BaseAction continueStage(String name) {
		for (SingleAction[] line : pool) {
			for (SingleAction target : line) {
				if (target.getName().equals(name)) {
					return target;
				}
			}
		}
		return null;
	}

	@Override
	public boolean hasButtons() {
		return pool != null && pool.length > 0;
	}

	@Override
	public boolean containButton(String string) {
		for (SingleAction[] line : pool) {
			for (SingleAction target : line) {
				if (target.getName().equals(string)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String[][] buildButton() {
		String[][] answer = new String[pool.length][];
		for (int i = 0; i < pool.length; i++) {
			answer[i] = new String[pool[i].length];
			for (int j = 0; j < pool[i].length; j++) {
				answer[i][j] = pool[i][j].getName();
			}
		}
		return answer;
	}




}
