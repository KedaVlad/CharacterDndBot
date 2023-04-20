package app.dnd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.model.hero.HeroCloud;

@Repository
public interface HeroCloudRepository extends MongoRepository<HeroCloud, Long> {

	@Aggregation(pipeline = { "{ $match: { id: { $eq: ?0 }, ownerName: { $eq: ?1 } } }", "{ $limit: 1 }" })
	Optional<HeroCloud> findByIdAndOwnerName(Long id, String ownerName);

	@Aggregation(pipeline = { "{ $match: { id: ?0 } }", "{ $group: { _id: '$ownerName' } }", "{ $project: { ownerName: '$_id', _id: 0 } }" })
	List<String> findDistinctOwnerNameById(Long id);
	
	void deleteByIdAndOwnerName(Long id, String ownerName);

	long countById(Long id);
}
