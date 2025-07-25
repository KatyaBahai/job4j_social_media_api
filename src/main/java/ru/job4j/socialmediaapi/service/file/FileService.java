package ru.job4j.socialmediaapi.service.file;

import ru.job4j.socialmediaapi.dto.FileDto;
import ru.job4j.socialmediaapi.model.File;
import ru.job4j.socialmediaapi.model.Post;

import java.util.Optional;

public interface FileService {

    Optional<File> saveFileFromDto(FileDto fileDto, Post post);

    Optional<File> save(FileDto fileDto);

    Optional<FileDto> getFileById(Long id);

    void deleteById(Long id);

    void clearOldFiles(Post post);
}
