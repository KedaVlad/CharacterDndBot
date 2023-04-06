package app.dnd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.model.ability.attacks.AttackAbility;

@Repository
public interface AttackAbilityRepository extends MongoRepository<AttackAbility, Long> {

}