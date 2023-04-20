package app.player.model.act;

public abstract class TreeActBuilder<T extends TreeActBuilder<T>> extends ActiveActBuilder<T>{

	protected boolean reply;
	protected boolean mediator;
	
	public T reply(boolean reply) {
		this.reply = reply;
		return self();
	}
	
	public T mediator(boolean mediator) {
		this.mediator = mediator;
		return self();
	}
	
}
