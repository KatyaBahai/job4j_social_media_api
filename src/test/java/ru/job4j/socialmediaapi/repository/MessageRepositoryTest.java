package ru.job4j.socialmediaapi.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.socialmediaapi.model.Message;
import ru.job4j.socialmediaapi.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void setUp() {
        messageRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveThenFindById() {
        User sender = userRepository.save(new User(null, "john", "email.ru", "pass"));
        User recipient = userRepository.save(new User(null, "jane", "email.com", "password"));
        Message message = Message.builder().text("question").sender(sender).recipient(recipient).build();
        messageRepository.save(message);
        var found = messageRepository.findById(message.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getText()).isEqualTo("question");
    }

    @Test
    public void whenFindAllThenReturnAll() {
        User sender = userRepository.save(new User(null, "john", "email.ru", "pass"));
        User recipient = userRepository.save(new User(null, "jane", "email.com", "password"));

        Message message1 = Message.builder().text("question").sender(recipient).recipient(sender).build();
        Message message2 = Message.builder().text("answer").sender(sender).recipient(recipient).build();
        messageRepository.save(message1);
        messageRepository.save(message2);

        var s = messageRepository.findAll();
        assertThat(s).hasSize(2);
        assertThat(s).extracting(Message::getText).contains("question", "answer");
    }

}