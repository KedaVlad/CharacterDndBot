package app.player.model.act;

import java.util.ArrayList;

import app.player.model.Stage;

public class CloudActBuilder extends ActiveActBuilder<CloudActBuilder> {

	private Stage stage;
	
	@Override
	protected CloudActBuilder self() {
		return this;
	}
	
	public CloudActBuilder stage(Stage stage) {
		this.stage = stage;
		return this;
	}
	
	public CloudAct build() {
		CloudAct act = new CloudAct();
		act.setActCircle(new ArrayList<>());
		act.setName(name);
		act.setStage(stage);
		return act;
	}

}
