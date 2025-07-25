package ru.job4j.socialmediaapi.model;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Builder
@Table(name = "files")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String path;
    @Column(name = "post_id")
    private Long postId;
}
