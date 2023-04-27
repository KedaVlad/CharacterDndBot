package app.dnd.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.model.telents.spells.MagicSoul;

@Repository
public interface MagicSoulRepository extends MongoRepository<MagicSoul, String> {

	@Aggregation(pipeline = {"{ $match: { userId: { $eq: ?0 }, ownerName: { $eq: ?1 } } }","{ $limit: 1 }"})
	Optional<MagicSoul> findByUserIdAndOwnerName(Long id, String ownerName);
	
	void deleteByUserIdAndOwnerName(Long id, String ownerName);

}

