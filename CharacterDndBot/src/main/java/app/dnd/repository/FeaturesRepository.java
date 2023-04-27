package app.dnd.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.model.telents.features.Features;

@Repository
public interface FeaturesRepository extends MongoRepository<Features, String> {

	@Aggregation(pipeline = {"{ $match: { userId: { $eq: ?0 }, ownerName: { $eq: ?1 } } }","{ $limit: 1 }"})
	Optional<Features> findByUserIdAndOwnerName(Long userId, String ownerName);
	void deleteByUserIdAndOwnerName(Long userId, String ownerName);
}

