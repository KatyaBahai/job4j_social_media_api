package ru.job4j.socialmediaapi.service.friendship;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.model.Friendship;
import ru.job4j.socialmediaapi.model.User;
import ru.job4j.socialmediaapi.repository.FriendshipRepository;
import ru.job4j.socialmediaapi.service.subscription.SubscriptionService;

import java.time.Instant;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class BasicFriendshipService implements FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final SubscriptionService subscriptionService;

    @Override
    public Friendship createFriendship(User user1, User user2) {
        Pair<User, User> friends = normalize(user1, user2);
        User friend1 = friends.getFirst();
        User friend2 = friends.getSecond();

        if (friendshipRepository.existsByFriend1AndFriend2(friend1, friend2)) {
            throw new IllegalStateException("Friendship already exists");
        }

        Friendship friendship = Friendship.builder()
                .friend1(friend1)
                .friend2(friend2)
                .friendsSince(Instant.now())
                .build();
        return friendshipRepository.save(friendship);
    }

    @Override
    public boolean existsByFriend1AndFriend2(User friend1, User friend2) {
        return friendshipRepository.existsByFriend1AndFriend2(friend1, friend2);
    }

    @Override
    public Optional<Friendship> findByFriend1AndFriend2(User friend1, User friend2) {
        return friendshipRepository.findByFriend1AndFriend2(friend1, friend2);
    }

    @Override
    public void removeFriendship(User initiator, User target) {
        Pair<User, User> friends = normalize(initiator, target);
        User friend1 = friends.getFirst();
        User friend2 = friends.getSecond();
        if (friendshipRepository.findByFriend1AndFriend2(friend1, friend2).isEmpty()) {
            throw new IllegalStateException("There's no friendship to annul.");
        }
        subscriptionService.deleteByFollowerAndFollowed(friend1, friend2);
        friendshipRepository.delete(friendshipRepository.findByFriend1AndFriend2(initiator, target).get());
    }

    private Pair<User, User> normalize(User u1, User u2) {
        return u1.getId() < u2.getId() ? Pair.of(u1, u2) : Pair.of(u2, u1);
    }
}
