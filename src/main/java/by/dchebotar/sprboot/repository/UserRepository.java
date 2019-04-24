package by.dchebotar.sprboot.repository;

import by.dchebotar.sprboot.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String name);

    Iterable<User> findByActive(boolean active);


    User findByActivationCode(String activationcode);
}
