package app.dnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dnd.service.roll.RollLogic;

@Service
public class DndFacade {

	@Autowired
	private HeroLogic heroLogic;
	@Autowired
	private StageHandler actionBuilder;
	@Autowired
	private RollLogic rollLogic;
	
	
	public HeroLogic hero() {
		return heroLogic;
	}

	
	public StageHandler action() {
		return actionBuilder;
	}
	
	public RollLogic roll() {
		return rollLogic;
	}
		
}
