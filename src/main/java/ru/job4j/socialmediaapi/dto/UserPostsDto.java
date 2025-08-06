package ru.job4j.socialmediaapi.dto;

import lombok.*;
import ru.job4j.socialmediaapi.model.Post;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserPostsDto {
    private long userId;
    private String userName;
    private List<Post> posts;
}
