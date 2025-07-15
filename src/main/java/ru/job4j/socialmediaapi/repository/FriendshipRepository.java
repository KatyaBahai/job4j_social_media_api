package ru.job4j.socialmediaapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmediaapi.model.Friendship;

public interface FriendshipRepository extends CrudRepository<Friendship, Long> {
}
