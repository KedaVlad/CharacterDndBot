package app.dnd.service.classes;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.hero.ClassDnd;
import app.dnd.model.hero.ClassesDnd;
import app.dnd.model.wrapp.ClassDndWrapp;
import app.dnd.service.lvl.LvlLogic;
import app.dnd.service.talants.logic.ProficienciesLogic;
import app.user.model.ActualHero;

@Component
public class ClassFacade implements ClassesLogic {

	@Autowired
	private ClassesDndService classesDndService;
	@Autowired
	private ClassDndWrappService classDndWrappService;
	@Autowired
	private ClassComandReader classComandReader;
	@Autowired
	private LvlLogic lvlLogic;
	@Autowired
	private ProficienciesLogic proficiencyLogic;
	
	@Override
	public boolean isReadyToGame(ActualHero actualHero) {
		return !classesDndService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName()).getDndClass().isEmpty();
	}
	@Override
	public void setClassDnd(ActualHero actualHero, String className, String archetype, int lvl) {
		
		ClassDndWrapp classDndWrapp = classDndWrappService.findByClassNameAndArchetype(className, archetype);
		lvlLogic.setLvl(actualHero, lvl);
		proficiencyLogic.upProf(actualHero, lvl);
		ClassDnd classDnd = new ClassDnd();
		classDnd.setClassName(classDndWrapp.className);
		classDnd.setArchetype(classDndWrapp.archetype);
		classDnd.setDiceHp(classDndWrapp.getDiceHp());
		classDnd.setFirstHp(classDndWrapp.getFirstHp());
		classDnd.setLvl(lvl);
		ClassesDnd classes = classesDndService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		classes.getDndClass().add(classDnd);
		classesDndService.save(classes);
		classComandReader.execute(actualHero, Arrays.copyOfRange(classDndWrapp.getGrowMap(), 0, lvl));
	}

}
