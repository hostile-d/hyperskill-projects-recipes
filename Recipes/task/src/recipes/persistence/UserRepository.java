package recipes.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.businesslayer.User;

import java.util.Collection;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Collection<User> findByEmail(String email);
}