package ru.job4j.socialmediaapi.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.socialmediaapi.service.post.PostService;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostsController {
    private final PostService postService;


}
