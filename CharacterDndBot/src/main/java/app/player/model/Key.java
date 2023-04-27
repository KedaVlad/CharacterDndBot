package app.player.model;

public enum Key {

	CLOUD(12345),
	TREE(54321),
	ArrayAct(10001);

	Key(int key) {
		this.KEY = key;
	} 
	
	public final int KEY;
}
