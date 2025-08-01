package ru.job4j.socialmediaapi.service.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.dto.FileDto;
import ru.job4j.socialmediaapi.model.File;
import ru.job4j.socialmediaapi.model.Post;
import ru.job4j.socialmediaapi.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class BasicFileService implements FileService {
    private final FileRepository fileRepository;
    private final String storageDirectory;

    public BasicFileService(FileRepository fileRepository,
                            @Value("${file.directory}") String storageDirectory) {
        this.fileRepository = fileRepository;
        this.storageDirectory = storageDirectory;
        createStorageDirectory(storageDirectory);
    }

    @Override
    public Optional<File> saveFileFromDto(FileDto fileDto, Post post) {
        String fileName = UUID.randomUUID() + fileDto.getName();
        var path = getNewFilePath(fileName);
        writeFileBytes(path, fileDto.getContent());
        return Optional.of(fileRepository.save(File.builder()
                .name(fileDto.getName())
                .path(path)
                .post(post)
                .build()));
    }

    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<File> save(FileDto fileDto) {
        String fileName = UUID.randomUUID() + fileDto.getName();
        var path = getNewFilePath(fileName);
        writeFileBytes(path, fileDto.getContent());
        return Optional.of(fileRepository.save(File.builder()
                .name(fileDto.getName())
                .path(path)
                .build()));
    }

    private String getNewFilePath(String fileName) {
        return storageDirectory + java.io.File.separator + fileName;
    }

    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<FileDto> getFileById(Long id) {
        var fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }
        var content = readFileAsBytes(fileOptional.get().getPath());
        return Optional.of(new FileDto(fileOptional.get().getName(), content));
    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        var fileOptional = fileRepository.findById(id);
        if (fileOptional.isPresent()) {
            deleteFile(fileOptional.get().getPath());
            fileRepository.deleteById(id);
        }
    }

    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearOldFiles(Post post) {
        Set<File> oldFiles = new HashSet<>(post.getFiles());
        post.getFiles().clear();
        for (File oldFile : oldFiles) {
            deleteById(oldFile.getId());
        }
    }
}
