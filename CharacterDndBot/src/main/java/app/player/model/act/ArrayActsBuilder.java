package app.player.model.act;

import java.util.ArrayList;
import app.player.model.Stage;

public class ArrayActsBuilder extends TreeActBuilder<ArrayActsBuilder> {
	
	private Stage[] pool;

	ArrayActsBuilder() {}

	public static ArrayActsBuilder builder() {
		return new ArrayActsBuilder();
	}

	public ArrayActsBuilder pool(Stage... pool) {
		this.pool = pool;
		return this;
	}

	public ArrayActs build() {
		ArrayActs answer = new ArrayActs();
		answer.setName(this.name);
		answer.setActCircle(new ArrayList<>());
		answer.setReply(this.reply);
		answer.setStages(this.pool);
		return answer;
	}

	@Override
	protected ArrayActsBuilder self() {
		return this;
	}
}
