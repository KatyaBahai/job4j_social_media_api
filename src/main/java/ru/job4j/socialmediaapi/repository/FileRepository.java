package ru.job4j.socialmediaapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmediaapi.model.File;

public interface FileRepository extends CrudRepository<File, Long> {
}
