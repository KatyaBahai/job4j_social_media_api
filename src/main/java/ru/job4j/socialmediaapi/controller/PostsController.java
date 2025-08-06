package ru.job4j.socialmediaapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.socialmediaapi.dto.UserPostsDto;
import ru.job4j.socialmediaapi.service.post.PostService;

import java.util.List;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostsController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<UserPostsDto>> getPostsByUserIds(List<Long> userIds) {
       List<UserPostsDto> dtoList = userIds.stream().map(postService::findUserPostsDtoByUserId).toList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dtoList);
    }

}
