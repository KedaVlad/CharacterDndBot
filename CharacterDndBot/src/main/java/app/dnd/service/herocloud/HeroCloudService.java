package app.dnd.service.herocloud;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import app.dnd.model.hero.HeroCloud;
import app.dnd.repository.HeroCloudRepository;

@Service
public class HeroCloudService {

	private HeroCloudRepository heroCloudRepository;
	
	public HeroCloud findByIdAndOwnerName(Long id, String ownerName) {
		Optional<HeroCloud> userOptional = heroCloudRepository.findByIdAndOwnerName(id, ownerName);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			HeroCloud heroCloud = HeroCloud.build(id, ownerName);
			heroCloudRepository.save(heroCloud);
			return heroCloud;
		}
	}
	
	public List<String> findDistinctOwnerNameById(Long id) {
		return heroCloudRepository.findDistinctOwnerNameById(id);
	}
	
	public long countHeroesById(Long id) {
        return heroCloudRepository.countById(id);
    }
	
	public void deleteByIdAndOwnerName(Long id, String ownerName) {
		heroCloudRepository.deleteByIdAndOwnerName(id, ownerName);
	}
	
	public void save(HeroCloud heroCloud) {
		heroCloudRepository.save(heroCloud);
	}
}
