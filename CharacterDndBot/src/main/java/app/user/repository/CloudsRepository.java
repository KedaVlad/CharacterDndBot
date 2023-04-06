package app.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.user.model.Clouds;

@Repository
public interface CloudsRepository extends MongoRepository<Clouds, Long> {

}
