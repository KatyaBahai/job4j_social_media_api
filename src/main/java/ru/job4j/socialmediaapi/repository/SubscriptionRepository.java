package ru.job4j.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmediaapi.model.Subscription;
import ru.job4j.socialmediaapi.model.User;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByFollowerAndFollowed(User follower, User followed);

    boolean existsByFollowerAndFollowed(User follower, User followed);

    int deleteByFollowerAndFollowed(User follower, User followed);

    List<Subscription> findByFollower(User follower);

    List<Subscription> findByFollowed(User followed);
}
