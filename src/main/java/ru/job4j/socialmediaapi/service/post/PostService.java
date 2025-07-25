package ru.job4j.socialmediaapi.service.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmediaapi.dto.FileDto;
import ru.job4j.socialmediaapi.model.Post;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface PostService {

    Optional<Post> createPostWithFiles(Post post, List<FileDto> fileDtos);

    Optional<Post> updatePost(Post updatedPost, List<FileDto> fileDtos);

    int deletePost(long postId);

    List<Post> findByUserId(long userId);

    List<Post> findByCreationDateBetween(Instant startDate, Instant endDate);

    Page<Post> findByOrderByCreationDateDesc(Pageable pageable);

    int updateHeadingAndDescription(String description, String heading, long id);

    int deleteFileFromPost(long fileId);

    Page<Post> findFollowedPostsByOrderByCreationDate(int followerId, Pageable pageable);

}
