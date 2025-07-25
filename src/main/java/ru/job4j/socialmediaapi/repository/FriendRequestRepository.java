package ru.job4j.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmediaapi.model.FriendRequest;
import ru.job4j.socialmediaapi.model.FriendRequestStatus;
import ru.job4j.socialmediaapi.model.User;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    boolean existsBySenderAndReceiverAndStatus(User sender, User receiver, Enum<FriendRequestStatus> status);

    Optional<FriendRequest> findBySenderAndReceiver(User sender, User receiver);
}
