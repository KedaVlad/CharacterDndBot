package app.config;

import org.springframework.data.mongodb.repository.MongoRepository;

import app.dnd.dto.ObjectDnd;

public interface ObjectDndRepository extends MongoRepository<ObjectDnd, String> {
}