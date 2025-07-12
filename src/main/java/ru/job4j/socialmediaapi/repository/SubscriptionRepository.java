package ru.job4j.socialmediaapi.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.socialmediaapi.model.Subscription;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
}
