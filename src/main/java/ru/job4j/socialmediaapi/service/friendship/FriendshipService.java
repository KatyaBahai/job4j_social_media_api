package ru.job4j.socialmediaapi.service.friendship;

import ru.job4j.socialmediaapi.model.Friendship;
import ru.job4j.socialmediaapi.model.User;

import java.util.Optional;

public interface FriendshipService {
    Friendship createFriendship(User user1, User user2);

    boolean existsByFriend1AndFriend2(User friend1, User friend2);

    Optional<Friendship> findByFriend1AndFriend2(User friend1, User friend2);

    void removeFriendship(User friend1, User friend2);
}
