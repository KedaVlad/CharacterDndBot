package app.dnd.model.actions;


public class PoolActionBuilder extends BaseActionBuilder<PoolActionBuilder> {

	private SingleAction[][] pool;

	PoolActionBuilder() {}

	public PoolActionBuilder actionsPool(SingleAction[][] pool) {
		this.pool = pool;
		return this;
	}

	public PoolActions build() {
		PoolActions action = new PoolActions();
		action.setText(text);
		action.setLocation(location);
		action.setPool(this.pool);
		return action;
	}

	@Override
	protected PoolActionBuilder self() {
		return this;
	}
}
