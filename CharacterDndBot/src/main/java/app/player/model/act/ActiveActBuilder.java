package app.player.model.act;

public abstract class ActiveActBuilder<T extends ActiveActBuilder<T>> {
	
	protected String name;

	protected abstract T self();

	public T name(String name) {
		this.name = name;
		return self();
	}
}
