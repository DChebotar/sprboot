package by.dchebotar.sprboot.repository;

import by.dchebotar.sprboot.domain.Appeal;
import by.dchebotar.sprboot.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface AppealRepository extends CrudRepository<Appeal, Long> {

    Iterable<Appeal> findByAuthor(User user);
}
