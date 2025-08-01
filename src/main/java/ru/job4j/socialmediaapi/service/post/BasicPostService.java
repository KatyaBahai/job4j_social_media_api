package ru.job4j.socialmediaapi.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.dto.FileDto;
import ru.job4j.socialmediaapi.model.File;
import ru.job4j.socialmediaapi.model.Post;
import ru.job4j.socialmediaapi.repository.PostRepository;
import ru.job4j.socialmediaapi.service.file.FileService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BasicPostService implements PostService {
    private final PostRepository postRepository;
    private final FileService fileService;

    @Transactional
    @Override
    public Optional<Post> createPostWithFiles(Post post, List<FileDto> fileDtos) {
        Post newPost = postRepository.save(post);
        saveNewFiles(newPost, fileDtos);
        return Optional.of(newPost);
    }

    private void saveNewFiles(Post post, List<FileDto> fileDtos) {
        Set<File> files = post.getFiles();
        for (FileDto fileDto : fileDtos) {
            var fileOpt = fileService.saveFileFromDto(fileDto, post);
            if (fileOpt.isPresent()) {
                File file = fileOpt.get();
                files.add(file);
            }
        }
    }

    @Transactional
    @Override
    public Optional<Post> updatePost(long postId, Post updatedPost, List<FileDto> fileDtos) {
        return postRepository.findById(postId).map(existingPost -> {
            existingPost.setHeading(updatedPost.getHeading());
            existingPost.setDescription(updatedPost.getDescription());
            fileService.clearOldFiles(existingPost);
            saveNewFiles(existingPost, fileDtos);
            return postRepository.save(existingPost);
        });
    }

    @Transactional
    @Override
    public Optional<Post> save(Post post) {
        return Optional.of(postRepository.save(post));
    }

    @Transactional
    @Override
    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Post> findByUserId(long userId) {
        return postRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Post> findById(long postId) {
        return postRepository.findById(postId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Post> findByCreationDateBetween(Instant startDate, Instant endDate) {
        return postRepository.findByCreationDateBetween(startDate, endDate);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Post> findByOrderByCreationDateDesc(Pageable pageable) {
        return postRepository.findByOrderByCreationDateDesc(pageable);
    }

    @Transactional
    @Override
    public int updateHeadingAndDescription(String description, String heading, long id) {
        return postRepository.updateHeadingAndDescription(description, heading, id);
    }

    @Transactional
    @Override
    public int deleteFileFromPost(long fileId) {
        return postRepository.deleteFileFromPost(fileId);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Post> findFollowedPostsByOrderByCreationDate(int followerId, Pageable pageable) {
        return postRepository.findFollowedPostsByOrderByCreationDate(followerId, pageable);
    }
}
