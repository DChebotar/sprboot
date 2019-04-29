package by.dchebotar.sprboot.service;

import by.dchebotar.sprboot.domain.Appeal;
import by.dchebotar.sprboot.domain.Status;
import by.dchebotar.sprboot.domain.User;
import by.dchebotar.sprboot.repository.AppealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class AppealService {

    @Autowired
    private AppealRepository appealRepository;

    public Iterable<Appeal> findAll() {
        return appealRepository.findAll();
    }


    public void save(Appeal appeal) {
        appealRepository.save(appeal);
    }

    public Iterable<Appeal> findByAuthor(User author) {
        return appealRepository.findByAuthor(author);
    }

    public void delete(Appeal appeal) {
        appealRepository.delete(appeal);
    }
}
