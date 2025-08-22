package ru.job4j.socialmediaapi.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.socialmediaapi.repository.PostRepository;
import ru.job4j.socialmediaapi.service.user.UserService;

@Service
@RequiredArgsConstructor
public class PostControllerSecurityService {
    private final PostRepository postRepository;
    private final UserService userService;

    public boolean isAuthor(Long postId, String username) throws IllegalArgumentException {
        return postRepository.findById(postId)
                .map(post -> userService.findById(post.getUserId()).orElseThrow().getName().equals(username))
                .orElse(false);
    }
}
