package app.dnd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.model.ability.spells.MagicSoul;

@Repository
public interface MagicSoulRepository extends MongoRepository<MagicSoul, Long> {

}

