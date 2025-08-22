package ru.job4j.socialmediaapi.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.socialmediaapi.model.Subscription;
import ru.job4j.socialmediaapi.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class SubscriptionRepositoryTest {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void setUp() {
        subscriptionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveThenFindById() {
        User userFollower = User.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .password("pass")
                .build();
        User userFollowed = User.builder()
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .password("password")
                .build();
        userRepository.save(userFollower);
        userRepository.save(userFollowed);

        Subscription subscription = Subscription.builder()
                .followed(userFollowed)
                .follower(userFollower)
                .isSubscribed(true)
                .build();
        subscriptionRepository.save(subscription);

        var found = subscriptionRepository.findById(subscription.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getFollower().getName()).isEqualTo("John Doe");
    }

    @Test
    public void whenFindAllThenReturnAll() {
        User userFollower = User.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .password("pass")
                .build();
        User userFollowed = User.builder()
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .password("password")
                .build();
        userRepository.save(userFollower);
        userRepository.save(userFollowed);

        Subscription subscription1 = Subscription.builder()
                .followed(userFollowed)
                .follower(userFollower)
                .isSubscribed(true)
                .build();

        Subscription subscription2 = Subscription.builder()
                .followed(userFollower)
                .follower(userFollowed)
                .isSubscribed(true)
                .build();

        subscriptionRepository.save(subscription1);
        subscriptionRepository.save(subscription2);
        var s = subscriptionRepository.findAll();
        assertThat(s).hasSize(2);
        assertThat(s).extracting(subscription -> subscription.getFollower().getName()).contains("John Doe", "Jane Doe");
    }
}