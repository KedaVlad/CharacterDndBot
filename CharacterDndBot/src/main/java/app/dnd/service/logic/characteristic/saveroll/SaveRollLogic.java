package app.dnd.service.logic.characteristic.saveroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.characteristics.SaveRoll;

public interface SaveRollLogic {

	void up(SaveRoll saveRoll, Long id);

}

@Component
class SaveRollFacade implements SaveRollLogic {

	@Autowired
	private SaveRollUp saveRollUp;
	
	@Override
	public void up(SaveRoll saveRoll, Long id) {
		saveRollUp.up(saveRoll, id);
	}
	
}