package app.dnd.service.talants;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dnd.model.telents.features.Feature;
import app.dnd.model.telents.features.Features;
import app.dnd.repository.FeaturesRepository;
import app.dnd.service.talants.logic.FeatureLogic;
import app.user.model.ActualHero;

@Transactional
@Service
public class FeaturesService implements FeatureLogic{

	@Autowired
	private FeaturesRepository featuresRepository;

	public Features findByIdAndOwnerName(Long id, String ownerName) {
		Optional< Features> userOptional = featuresRepository.findByIdAndOwnerName(id, ownerName);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return Features.build(id);
		}
	}

	public void save(Features features) {
		featuresRepository.save(features);
	}

	public void deleteByIdAndOwnerName(Long id, String name) {
		featuresRepository.deleteByIdAndOwnerName(id, name);
	}

	@Override
	public void addTrait(ActualHero hero, Feature object) {
		Features features = findByIdAndOwnerName(hero.getId(), hero.getName());
		features.getByRace().add(object);
		save(features);	
	}

}
