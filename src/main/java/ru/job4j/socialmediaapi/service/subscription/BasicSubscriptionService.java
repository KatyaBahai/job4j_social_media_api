package ru.job4j.socialmediaapi.service.subscription;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.model.*;
import ru.job4j.socialmediaapi.repository.SubscriptionRepository;
import ru.job4j.socialmediaapi.service.friendship.FriendshipService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class BasicSubscriptionService implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription subscribe(User follower, User followed) {
        if (subscriptionRepository.findByFollowerAndFollowed(follower, followed).isEmpty()) {
            throw new IllegalStateException("Subscription already exists");
        }
        Subscription subscription = Subscription.builder()
                .follower(follower)
                .followed(followed)
                .createdAt(Instant.now())
                .isSubscribed(true)
                .build();
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Optional<Subscription> findByFollowerAndFollowed(User follower, User followed) {
        return subscriptionRepository.findByFollowerAndFollowed(follower, followed);
    }

    @Override
    public boolean existsByFollowerAndFollowed(User follower, User followed) {
        return subscriptionRepository.existsByFollowerAndFollowed(follower, followed);
    }

    @Override
    public void unsubscribe(User follower, User followed) {
        if (subscriptionRepository.findByFollowerAndFollowed(followed, follower).isPresent()) {
            throw new IllegalStateException("You can't unsubscribe from your friend, you must unfriend them first.");
        }
        subscriptionRepository.deleteByFollowerAndFollowed(follower, followed);
    }

    @Override
    public List<Subscription> findByFollower(User follower) {
        return subscriptionRepository.findByFollower(follower);
    }

    @Override
    public List<Subscription> findByFollowed(User followed) {
        return subscriptionRepository.findByFollowed(followed);
    }
}
