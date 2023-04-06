package app.dnd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.model.characteristics.Memoirs;

@Repository
public interface MemoirsRepository extends MongoRepository<Memoirs, Long> {

}

