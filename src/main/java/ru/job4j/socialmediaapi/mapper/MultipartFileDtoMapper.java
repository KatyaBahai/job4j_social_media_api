package ru.job4j.socialmediaapi.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.socialmediaapi.dto.FileDto;

import java.util.ArrayList;
import java.util.List;

public class MultipartFileDtoMapper {

    public static List<FileDto> convertMultiparttoDto(List<MultipartFile> multipartFiles) {
        List<FileDto> dtoFiles = new ArrayList<>();
        try {
            for (MultipartFile file : multipartFiles) {
                dtoFiles.add(new FileDto(file.getOriginalFilename(), file.getBytes()));
            }
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to process uploaded files", exception);
        }
        return dtoFiles;
    }
}
