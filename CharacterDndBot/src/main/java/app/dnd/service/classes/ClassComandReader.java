package app.dnd.service.classes;

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
import app.dnd.model.stuffs.items.Items;
import app.dnd.model.telents.attacks.AttackModification;
import app.dnd.model.telents.features.Feature;
import app.dnd.model.telents.features.Features;
import app.dnd.model.telents.features.InerFeature;
import app.dnd.model.telents.proficiency.Possession;
import app.dnd.model.telents.spells.MagicSoul;
import app.dnd.model.telents.spells.Spell;
import app.dnd.service.inerComand.InerComandExecutor;
import app.dnd.service.talants.FeaturesService;
import app.user.model.ActualHero;

@Service
public class ClassComandReader {

	@Autowired
	private FeaturesService featuresService;
	@Autowired
	private InerComandExecutor inerComandExecutor;


	public void execute(ActualHero actualHero, InerComand[][] inerComands) {
		
		ComandConteiner comandConteiner = new ComandConteiner();
		List<Feature> feature = new ArrayList<>();
		for(InerComand[] inerComandArr: inerComands) {
			for(InerComand inerComand: inerComandArr) {
				handle(comandConteiner, feature, inerComand);
			}
		}
		Features features = featuresService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		features.getByClasses().addAll(feature);
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


	private void add(ComandConteiner comandConteiner, List<Feature> feature, AddComand comand) {

		ObjectDnd[] objects = comand.getTargets();
		for (ObjectDnd object : objects) {
			if(object instanceof InerComand) {
				handle(comandConteiner, feature, (InerComand) object);
			} else if (object instanceof Feature) {
				feature.add((Feature) object);
				if (object instanceof InerFeature) {
					InerFeature target = (InerFeature) object;
					for (InerComand inerComand : target.getComand()) {
						handle(comandConteiner, feature, inerComand);
					}
				}
			} else if (object instanceof Possession
					|| object instanceof MagicSoul
					|| object instanceof Spell
					|| object instanceof AttackModification
					|| object instanceof Items) {
				comandConteiner.getAdd().add(object);
			}
		}
	}
}
