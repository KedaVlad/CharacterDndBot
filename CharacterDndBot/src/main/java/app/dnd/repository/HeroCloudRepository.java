package app.dnd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.model.hero.HeroCloud;

@Repository
public interface HeroCloudRepository extends MongoRepository<HeroCloud, String> {

	@Aggregation(pipeline = {"{ $match: { userId: { $eq: ?0 }, ownerName: { $eq: ?1 } } }","{ $limit: 1 }"})
	Optional<HeroCloud> findByUserIdAndOwnerName(Long id, String ownerName);

	@Aggregation(pipeline = { "{ $match: { userId: { $eq: ?0 } } }", "{ $group: { _id: '$ownerName' } }", "{ $project: { ownerName: '$_id', _id: 0 } }" })
	List<String> findDistinctOwnerNameByUserId(Long userId);
	
	void deleteByUserIdAndOwnerName(Long userId, String ownerName);

	long countByUserId(Long userId);
}
