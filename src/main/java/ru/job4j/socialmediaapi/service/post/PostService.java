package ru.job4j.socialmediaapi.service.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmediaapi.dto.FileDto;
import ru.job4j.socialmediaapi.dto.UserPostsDto;
import ru.job4j.socialmediaapi.model.Post;
import ru.job4j.socialmediaapi.model.User;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface PostService {

    Optional<Post> createPostWithFiles(Post post, List<FileDto> fileDtos);

    Optional<Post> updatePost(long postId, Post updatedPost, List<FileDto> fileDtos);

    void deletePost(Post post);

    boolean deleteById(long postId);

    Optional<Post> editHeadingAndDescription(String heading, String description, long postId);

    Optional<Post> save(Post post);

    List<Post> findByUserId(long userId);

    Optional<Post> findById(long postId);

    List<Post> findByCreationDateBetween(Instant startDate, Instant endDate);

    Page<Post> findByOrderByCreationDateDesc(Pageable pageable);

    int updateHeadingAndDescription(String description, String heading, long id);

    int deleteFileFromPost(long fileId);

    Page<Post> findFollowedPostsByOrderByCreationDate(int followerId, Pageable pageable);

    UserPostsDto findUserPostsDtoByUserId(long userId);
}
