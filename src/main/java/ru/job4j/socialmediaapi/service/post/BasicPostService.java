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

@Transactional
@Service
@RequiredArgsConstructor
public class BasicPostService implements PostService {
    private final PostRepository postRepository;
    private final FileService fileService;

    @Override
    public Optional<Post> createPostWithFiles(Post post, List<FileDto> fileDtos) {
        saveNewFiles(post, fileDtos);
        return Optional.of(postRepository.save(post));
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

    @Override
    public Optional<Post> updatePost(Post updatedPost, List<FileDto> fileDtos) {
        return postRepository.findById(updatedPost.getId()).map(existingPost -> {
            existingPost.setHeading(updatedPost.getHeading());
            existingPost.setDescription(updatedPost.getDescription());
            fileService.clearOldFiles(existingPost);
            saveNewFiles(existingPost, fileDtos);
            return postRepository.save(existingPost);
        });
    }

    @Override
    public int deletePost(long postId) {
        return postRepository.deletePost(postId);
    }

    @Override
    public List<Post> findByUserId(long userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public List<Post> findByCreationDateBetween(Instant startDate, Instant endDate) {
        return postRepository.findByCreationDateBetween(startDate, endDate);
    }

    @Override
    public Page<Post> findByOrderByCreationDateDesc(Pageable pageable) {
        return postRepository.findByOrderByCreationDateDesc(pageable);
    }

    @Override
    public int updateHeadingAndDescription(String description, String heading, long id) {
        return postRepository.updateHeadingAndDescription(description, heading, id);
    }

    @Override
    public int deleteFileFromPost(long fileId) {
        return postRepository.deleteFileFromPost(fileId);
    }

    @Override
    public Page<Post> findFollowedPostsByOrderByCreationDate(int followerId, Pageable pageable) {
        return postRepository.findFollowedPostsByOrderByCreationDate(followerId, pageable);
    }
}
