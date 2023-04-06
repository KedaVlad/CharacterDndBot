package app.dnd.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DndFacade {

	@Autowired
	private ButtonLogic buttonLogic;
	@Autowired
	private InformatorLogic informatorLogic;
	@Autowired
	private HeroLogic heroLogic;
	@Autowired
	private ActionBuilder actionBuilder;
	
	public HeroLogic hero() {
		return heroLogic;
	}
	
	public ButtonLogic buttons() {
		return buttonLogic;
	}
	
	public InformatorLogic information() {
		return informatorLogic;
	}
	
	public ActionBuilder action() {
		return actionBuilder;
	}
		
}
