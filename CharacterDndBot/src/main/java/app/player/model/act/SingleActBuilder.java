package app.player.model.act;

import java.util.ArrayList;

import app.player.model.Stage;

public class SingleActBuilder extends TreeActBuilder<SingleActBuilder> {
	
	private Stage stage;

	SingleActBuilder() {
	}

	public SingleActBuilder stage(Stage stage) {
		this.stage = stage;
		return this;
	}

	public SingleAct build() {
		SingleAct answer = new SingleAct();
		answer.setName(this.name);
		answer.setActCircle(new ArrayList<>());
		answer.setReply(this.reply);
		answer.setMediator(this.mediator);
		answer.setStage(this.stage);
		return answer;
	}

	@Override
	protected SingleActBuilder self() {
		return this;
	}
}