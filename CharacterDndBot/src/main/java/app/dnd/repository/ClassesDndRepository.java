package app.dnd.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.model.hero.ClassesDnd;

@Repository
public interface ClassesDndRepository extends MongoRepository<ClassesDnd, String> {

	@Aggregation(pipeline = {"{ $match: { userId: { $eq: ?0 }, ownerName: { $eq: ?1 } } }","{ $limit: 1 }"})
	Optional<ClassesDnd> findByUserIdAndOwnerName(Long id, String ownerName);
	void deleteByUserIdAndOwnerName(Long id, String ownerName);

}