package ru.job4j.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmediaapi.model.File;

public interface FileRepository extends JpaRepository<File, Long> {
}
