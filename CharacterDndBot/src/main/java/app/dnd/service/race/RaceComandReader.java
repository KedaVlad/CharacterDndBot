package app.dnd.service.race;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dnd.model.ObjectDnd;
import app.dnd.model.comands.AddComand;
import app.dnd.model.comands.CloudComand;
import app.dnd.model.comands.ComandConteiner;
import app.dnd.model.comands.InerComand;
import app.dnd.model.comands.UpComand;
import app.dnd.model.telents.features.Feature;
import app.dnd.model.telents.features.Features;
import app.dnd.model.telents.features.InerFeature;
import app.dnd.model.telents.proficiency.Possession;
import app.dnd.service.inerComand.InerComandExecutor;
import app.dnd.service.talants.FeaturesService;
import app.user.model.ActualHero;

@Service
public class RaceComandReader {

	@Autowired
	private FeaturesService featuresService;
	@Autowired
	private InerComandExecutor inerComandExecutor;

	public void execute(ActualHero actualHero, InerComand[] inerComands) {

		ComandConteiner comandConteiner = new ComandConteiner();
		List<Feature> trait = new ArrayList<>();
		for(InerComand inerComand: inerComands) {
			handle(comandConteiner, trait, inerComand);
		}
		Features features = featuresService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		features.getByRace().addAll(trait);
		featuresService.save(features);
		inerComandExecutor.execute(actualHero, comandConteiner);
	}

	public void handle(ComandConteiner comandConteiner, List<Feature> trait, InerComand inerComands) {
		
		if (inerComands instanceof AddComand) {
			add(comandConteiner, trait, (AddComand) inerComands);
		} else if (inerComands instanceof UpComand) {			
			comandConteiner.getUp().add(((UpComand) inerComands).getObjectDnd());
		} else if (inerComands instanceof CloudComand) {
			comandConteiner.getCloud().add((CloudComand) inerComands);
		}
	}


	private void add(ComandConteiner comandConteiner, List<Feature> trait, AddComand comand) {
		
		ObjectDnd[] objects = comand.getTargets();
		for (ObjectDnd object : objects) {
			if(object instanceof InerComand) {
				handle(comandConteiner, trait, (InerComand) object);
			} else if (object instanceof Feature) {
				trait.add((Feature) object);
				if (object instanceof InerFeature) {
					InerFeature target = (InerFeature) object;
					for (InerComand inerComand : target.getComand()) {
						handle(comandConteiner, trait, inerComand);
					}
				}
			} else if (object instanceof Possession) {
				comandConteiner.getAdd().add((Possession) object);
			}
		}
	}
}
