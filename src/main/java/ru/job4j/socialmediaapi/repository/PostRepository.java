package ru.job4j.socialmediaapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmediaapi.model.Post;

import java.awt.print.Pageable;
import java.time.Instant;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserId(long userId);

    List<Post> findByCreationDateBetween(Instant startDate, Instant endDate);

    Page<Post> findByOrderByCreationDateDesc(Pageable pageable);

}
