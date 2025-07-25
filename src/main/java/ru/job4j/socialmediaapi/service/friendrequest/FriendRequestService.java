package ru.job4j.socialmediaapi.service.friendrequest;

import ru.job4j.socialmediaapi.model.FriendRequest;
import ru.job4j.socialmediaapi.model.FriendRequestStatus;
import ru.job4j.socialmediaapi.model.User;

import java.util.Optional;

public interface FriendRequestService {
    FriendRequest updateFriendRequest(User sender, User receiver, FriendRequestStatus status);

    Optional<FriendRequest> createOrFindFriendRequest(User sender, User receiver, FriendRequestStatus status);

    Optional<FriendRequest> sendFriendRequest(User sender, User receiver);

    FriendRequest acceptFriendRequest(User sender, User receiver);

    FriendRequest rejectFriendRequest(User sender, User receiver);

    FriendRequest cancelFriendRequest(User sender, User receiver);
}
