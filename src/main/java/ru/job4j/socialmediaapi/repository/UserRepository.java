package ru.job4j.socialmediaapi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmediaapi.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("from User u WHERE u.email = :email AND u.password = :password")
    Optional<User> findByEmialAndPassword(
            @Param("email") String email,
            @Param("password") String password);

    @Query("""
            SELECT s.follower
            FROM Subscription s
            WHERE s.followed.id = :followedId
            AND s.isSubscribed = true
            """)
    List<User> findAllFollowers(@Param("followedId") long followedId);

    @Query("""
             SELECT
                    CASE
                        WHEN f.friend1.id = :friendId THEN f.friend2
                        ELSE f.friend1
                    END
                FROM Friendship f
                WHERE f.friend1.id = :friendId OR f.friend2.id = :friendId
            """)
    List<User> findAllFriends(@Param("friendId") long friendId);
}
