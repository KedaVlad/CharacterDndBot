package app.dnd.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.model.stuffs.Wallet;

@Repository
public interface WalletRepository extends MongoRepository<Wallet, Long> {

	@Aggregation(pipeline = { "{ $match: { id: { $eq: ?0 }, ownerName: { $eq: ?1 } } }", "{ $limit: 1 }" })
	Optional<Wallet> findByIdAndOwnerName(Long id, String ownerName);
	
	void deleteByIdAndOwnerName(Long id, String ownerName);
}
