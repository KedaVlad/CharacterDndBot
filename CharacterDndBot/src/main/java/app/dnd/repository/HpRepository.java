package app.dnd.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.model.ability.Hp;

@Repository
public interface HpRepository extends MongoRepository<Hp, String> {

	@Aggregation(pipeline = {"{ $match: { userId: { $eq: ?0 }, ownerName: { $eq: ?1 } } }","{ $limit: 1 }"})
	Optional<Hp> findByUserIdAndOwnerName(Long userId, String ownerName);
	void deleteByUserIdAndOwnerName(Long userId, String name);
}
