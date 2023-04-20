package app.dnd.util.pools;

public class SimplePool<T> extends Pool<T> {
 
	private int activeMaxSize;

	public void add(T object) {
		if (active.size() >= activeMaxSize) {
			active.add(object);
		}
	}

	public void giveBack(T object) {
		this.active.remove(object);
	}
	
	public void giveBack(int target) {
		this.active.remove(target);
	}

	public int getActiveMaxSize() {
		return activeMaxSize;
	}

	public void setActiveMaxSize(int activeMaxSize) {
		this.activeMaxSize = activeMaxSize;
	}

}
