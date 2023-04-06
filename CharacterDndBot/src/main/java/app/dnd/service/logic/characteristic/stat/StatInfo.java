package app.dnd.service.logic.characteristic.stat;

import app.dnd.model.characteristics.Stat;

public interface StatInfo {
	
	public String menu();
	public String targetFirst(Stat stat);
}

class StatInformator implements StatInfo {

	@Override
	public String menu() {
		return "Choose stat which you want to roll or change";
	}

	@Override
	public String targetFirst(Stat stat) {
		return stat.getName().toString() + " " + stat.getValue();
	}
	
}
