package ru.job4j.socialmediaapi.service.user;

import ru.job4j.socialmediaapi.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findByEmailAndPassword(String email, String password);

    List<User> findAllFollowers(long followedId);

    List<User> findAllFollowedByUser(long followerId);

    List<User> findAllFriends(long friendId);

    Optional<User> save(User user);

    Optional<User> findById(long id);

    void deleteById(long id);

    Optional<User> update(long userId, User user);
}
