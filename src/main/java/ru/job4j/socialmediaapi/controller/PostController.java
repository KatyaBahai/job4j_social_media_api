package ru.job4j.socialmediaapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmediaapi.dto.FileDto;
import ru.job4j.socialmediaapi.mapper.MultipartFileDtoMapper;
import ru.job4j.socialmediaapi.model.Post;
import ru.job4j.socialmediaapi.service.post.PostService;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<Post> get(@PathVariable("postId") Long postId) {
        return postService.findById(postId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Post> save(@RequestPart("post") Post post,
                                     @RequestPart("multipartFiles") List<MultipartFile> multipartFiles) {
        List<FileDto> dtoFiles = MultipartFileDtoMapper.convertMultiparttoDto(multipartFiles);
        postService.createPostWithFiles(post, dtoFiles);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(post);
    }

    @PutMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestPart Post post,
                       @PathVariable("postId") long postId,
                       @RequestPart List<MultipartFile> multipartFiles) {
        List<FileDto> dtoFiles = MultipartFileDtoMapper.convertMultiparttoDto(multipartFiles);
        postService.updatePost(postId, post, dtoFiles);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeById(@PathVariable long postId) {
        if (postService.findById(postId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        postService.deletePost(postService.findById(postId).get());
    }
}
