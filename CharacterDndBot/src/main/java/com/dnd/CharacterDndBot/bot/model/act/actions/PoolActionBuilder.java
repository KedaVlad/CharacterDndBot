package com.dnd.CharacterDndBot.bot.model.act.actions;

public class PoolActionBuilder extends BaseActionBuilder<PoolActionBuilder> {

	private BaseAction[][] pool;

	PoolActionBuilder() {}

	public PoolActionBuilder actionsPool(BaseAction[][] pool) {
		this.pool = pool;
		return this;
	}

	public PoolActions build() {
		PoolActions action = new PoolActions();
		action.setReplyButtons(replyButtons);
		action.setMediator(mediator);
		action.setName(name);
		action.setLocation(location);
		if (this.cloud) {
			action.setCloud(cloud);
			BaseAction[][] newPool = new BaseAction[this.pool.length + 1][];
			for (int i = 0; i < this.pool.length; i++) {
				newPool[i] = this.pool[i];
			}
			newPool[this.pool.length] = new BaseAction[] { Action.builder().name("ELIMINATION").build() };
			this.pool = newPool;
		}
		action.setPool(this.pool);
		return action;
	}

	@Override
	protected PoolActionBuilder self() {
		return this;
	}
}
