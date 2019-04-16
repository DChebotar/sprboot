package by.dchebotar.sprboot.repository;

import by.dchebotar.sprboot.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
