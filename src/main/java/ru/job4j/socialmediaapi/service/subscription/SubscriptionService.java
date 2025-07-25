package ru.job4j.socialmediaapi.service.subscription;

import ru.job4j.socialmediaapi.model.Subscription;
import ru.job4j.socialmediaapi.model.User;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    Subscription createSubscription(User follower, User followed);

    Optional<Subscription> findByFollowerAndFollowed(User follower, User followed);

    boolean existsByFollowerAndFollowed(User follower, User followed);

    void unsubscribe(User follower, User followed);

    List<Subscription> findByFollower(User follower);

    List<Subscription> findByFollowed(User followed);

    int deleteByFollowerAndFollowed(User follower, User followed);
}
