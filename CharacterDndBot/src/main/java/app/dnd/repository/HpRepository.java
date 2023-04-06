package app.dnd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.model.characteristics.Hp;

@Repository
public interface HpRepository extends MongoRepository<Hp, Long> {

}
