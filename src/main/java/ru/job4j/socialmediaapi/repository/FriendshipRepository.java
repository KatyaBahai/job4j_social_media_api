package ru.job4j.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmediaapi.model.Friendship;
import ru.job4j.socialmediaapi.model.User;

import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    boolean existsByFriend1AndFriend2(User friend1, User friend2);

    Optional<Friendship> findByFriend1AndFriend2(User friend1, User friend2);
}
