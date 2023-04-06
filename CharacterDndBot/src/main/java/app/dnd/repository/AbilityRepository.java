package app.dnd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.model.ability.Ability;

@Repository
public interface AbilityRepository extends MongoRepository<Ability, Long> {

}

