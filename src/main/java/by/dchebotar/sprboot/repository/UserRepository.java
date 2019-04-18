package by.dchebotar.sprboot.repository;

import by.dchebotar.sprboot.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String name);

}
