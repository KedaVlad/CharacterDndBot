package app.dnd.model.actions;

public abstract class SingleActionBuilder<T extends SingleActionBuilder<T>> extends BaseActionBuilder<T> {
	
	protected String name;

	public T name(String name) {
		this.name = name;
		return self();
	}
}
