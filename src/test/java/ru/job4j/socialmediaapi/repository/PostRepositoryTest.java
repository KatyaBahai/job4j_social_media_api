package ru.job4j.socialmediaapi.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.socialmediaapi.model.Post;
import ru.job4j.socialmediaapi.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    public void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveThenFindById() {
        User user = userRepository.save(new User(null, "john", "email.ru", "pass"));
        Post post = Post.builder().heading("Heading").userId(user.getId()).build();
        postRepository.save(post);
        var found = postRepository.findById(post.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getHeading()).isEqualTo("Heading");
    }

    @Test
    public void whenFindAllThenReturnAll() {
        User user = userRepository.save(new User(null, "john", "email.ru", "pass"));
        Post post1 = Post.builder().heading("Heading1").userId(user.getId()).build();
        Post post2 = Post.builder().heading("Heading2").userId(user.getId()).build();
        postRepository.save(post1);
        postRepository.save(post2);
        var s = postRepository.findAll();
        assertThat(s).hasSize(2);
        assertThat(s).extracting(Post::getHeading).contains("Heading1", "Heading2");
    }

}