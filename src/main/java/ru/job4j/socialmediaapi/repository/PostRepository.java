package ru.job4j.socialmediaapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmediaapi.model.Post;

import java.time.Instant;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserId(long userId);

    List<Post> findByCreationDateBetween(Instant startDate, Instant endDate);

    Page<Post> findByOrderByCreationDateDesc(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE Post p
            SET p.description = :description, p.heading = :heading
            WHERE p.id = :id""")
    int updateHeadingAndDescription(
            @Param("description") String description,
            @Param("heading") String heading,
            @Param("id") long id);

    @Modifying(clearAutomatically = true)
    @Query("""
            DELETE from File f
            WHERE f.id = :fileId""")
    int deleteFileFromPost(@Param("fileId") long fileId);

    @Modifying(clearAutomatically = true)
    @Query("""
            DELETE from Post p
            WHERE p.id = :id""")
    int deletePost(@Param("id") long id);

    @Query("""
            SELECT p from Post p
            where p.userId IN
            (SELECT s.followed.id
            from Subscription s
            WHERE s.follower.id = :followerId)
            ORDER BY p.creationDate DESC
            """)
    Page<Post> findFollowedPostsByOrderByCreationDate(
            @Param("followerId") int followerId,
            Pageable pageable);
}
