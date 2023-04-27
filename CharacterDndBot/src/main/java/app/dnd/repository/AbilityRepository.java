package app.dnd.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.model.ability.Ability;

@Repository
public interface AbilityRepository extends MongoRepository<Ability, String> {

	@Aggregation(pipeline = {"{ $match: { userId: { $eq: ?0 }, ownerName: { $eq: ?1 } } }","{ $limit: 1 }"})
	Optional<Ability> findByUserIdAndOwnerName(Long userId, String ownerName);

	void deleteByUserIdAndOwnerName(Long userId, String ownerName);

}
