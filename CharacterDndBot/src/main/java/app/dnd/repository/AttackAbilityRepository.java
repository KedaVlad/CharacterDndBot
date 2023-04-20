package app.dnd.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.model.telents.attacks.AttackAbility;

@Repository
public interface AttackAbilityRepository extends MongoRepository<AttackAbility, Long> {

	@Aggregation(pipeline = { "{ $match: { id: { $eq: ?0 }, ownerName: { $eq: ?1 } } }", "{ $limit: 1 }" })
	Optional<AttackAbility> findByIdAndOwnerName(Long id, String ownerName);
	
	void deleteByIdAndOwnerName(Long id, String ownerName);
}