package ru.job4j.socialmediaapi.service.friendrequest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.model.FriendRequest;
import ru.job4j.socialmediaapi.model.FriendRequestStatus;
import ru.job4j.socialmediaapi.model.Friendship;
import ru.job4j.socialmediaapi.model.User;
import ru.job4j.socialmediaapi.repository.FriendRequestRepository;
import ru.job4j.socialmediaapi.service.friendship.FriendshipService;
import ru.job4j.socialmediaapi.service.subscription.SubscriptionService;

import java.time.Instant;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class BasicFriendRequestService implements FriendRequestService {
    private final FriendRequestRepository friendRequestRepository;
    private final SubscriptionService subscriptionService;
    private final FriendshipService friendshipService;

    @Override
    public FriendRequest updateFriendRequest(User sender, User receiver, FriendRequestStatus status) {
        Optional<FriendRequest> friendRequestOpt = friendRequestRepository.findBySenderAndReceiver(sender, receiver);
        if (friendRequestOpt.isEmpty()) {
            throw new IllegalStateException("Friend request not found between sender and receiver.");
        }
        FriendRequest friendRequest = friendRequestOpt.get();
        friendRequest.setStatus(status);
        return friendRequestRepository.save(friendRequest);
    }

    @Override
    public Optional<FriendRequest> createOrFindFriendRequest(User sender, User receiver, FriendRequestStatus status) {
        if (!friendRequestRepository.existsBySenderAndReceiverAndStatus(sender, receiver, status)) {
            FriendRequest friendRequest = FriendRequest.builder()
                    .createdAt(Instant.now())
                    .sender(sender)
                    .receiver(receiver)
                    .status(FriendRequestStatus.PENDING)
                    .build();
            return Optional.of(friendRequestRepository.save(friendRequest));
        }
        Example<FriendRequest> friendRequestExample = Example.of(
                FriendRequest.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .build());
        return friendRequestRepository.findOne(friendRequestExample);
    }

    @Override
    public Optional<FriendRequest> sendFriendRequest(User sender, User receiver) {
        subscriptionService.createSubscription(sender, receiver);
        return createOrFindFriendRequest(sender, receiver, FriendRequestStatus.PENDING);
    }

    @Override
    public FriendRequest acceptFriendRequest(User sender, User receiver) {
        if (subscriptionService.findByFollowerAndFollowed(sender, receiver).isEmpty()) {
            subscriptionService.createSubscription(sender, receiver);
        }
        subscriptionService.createSubscription(receiver, sender);

        friendshipService.createFriendship(sender, receiver);

        return updateFriendRequest(sender, receiver, FriendRequestStatus.ACCEPTED);
    }

    @Override
    public FriendRequest rejectFriendRequest(User sender, User receiver) {
        if (!friendRequestRepository.existsBySenderAndReceiverAndStatus(sender, receiver, FriendRequestStatus.PENDING)) {
            throw new IllegalStateException("No pending friend request to reject.");
        }
        FriendRequest friendRequest = friendRequestRepository.findBySenderAndReceiver(sender, receiver).get();
        friendRequest.setStatus(FriendRequestStatus.DECLINED);
        return friendRequestRepository.save(friendRequest);
    }

    @Override
    public FriendRequest cancelFriendRequest(User sender, User receiver) {
        if (!friendRequestRepository.existsBySenderAndReceiverAndStatus(sender, receiver, FriendRequestStatus.PENDING)) {
            throw new IllegalStateException("No pending friend request to cancel.");
        }
        FriendRequest friendRequest = friendRequestRepository.findBySenderAndReceiver(sender, receiver).get();
        friendRequest.setStatus(FriendRequestStatus.CANCELLED);
        return friendRequestRepository.save(friendRequest);
    }
}
