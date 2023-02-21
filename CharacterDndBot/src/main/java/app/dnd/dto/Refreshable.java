package app.dnd.dto;

public interface Refreshable {

	public enum Time {
		LONG("LONG"), SHORT("SHORT");

		String name;

		Time(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}

	}

	abstract void refresh(Time time);
}
