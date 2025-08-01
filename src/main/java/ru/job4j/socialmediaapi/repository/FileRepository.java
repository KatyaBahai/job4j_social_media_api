package ru.job4j.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import ru.job4j.socialmediaapi.model.File;

public interface FileRepository extends JpaRepository<File, Long> {

    @Modifying
    void deleteAllByPostId(long postId);
}
