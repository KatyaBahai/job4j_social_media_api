package ru.job4j.socialmediaapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.socialmediaapi.dto.UserPostsDto;
import ru.job4j.socialmediaapi.model.Post;
import ru.job4j.socialmediaapi.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserPostsDtoMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "posts", target = "posts")
    UserPostsDto toDto(List<Post> posts, User user);
}
