package by.dchebotar.sprboot.repository;

import by.dchebotar.sprboot.domain.Application;
import by.dchebotar.sprboot.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationRepository extends CrudRepository<Application, Long> {

    Iterable<Application> findByAuthor(User user);
}
