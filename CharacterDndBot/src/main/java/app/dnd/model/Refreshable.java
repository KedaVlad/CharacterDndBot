package app.dnd.model;

public interface Refreshable {

	enum Time {
		LONG("LONG"), SHORT("SHORT");

		final String name;

		Time(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}

	}

	void refresh(Time time);
}
