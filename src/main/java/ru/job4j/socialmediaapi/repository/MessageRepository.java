package ru.job4j.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmediaapi.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
