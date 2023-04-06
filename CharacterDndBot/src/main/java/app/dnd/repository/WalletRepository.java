package app.dnd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.model.stuffs.Wallet;

@Repository
public interface WalletRepository extends MongoRepository<Wallet, Long> {

}
