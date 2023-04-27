package app.dnd.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.model.ability.Lvl;

@Repository
public interface LvlRepository extends MongoRepository<Lvl, String> {

	@Aggregation(pipeline = {"{ $match: { userId: { $eq: ?0 }, ownerName: { $eq: ?1 } } }","{ $limit: 1 }"})
	Optional<Lvl> findByUserIdAndOwnerName(Long userId, String ownerName);
	
	void deleteByUserIdAndOwnerName(Long userId, String name);
}

