package app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFileRepository extends CrudRepository<UserFile, Long> {

}
