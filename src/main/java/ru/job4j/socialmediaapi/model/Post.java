package ru.job4j.socialmediaapi.model;

import lombok.*;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "files")
public class Post {
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String heading;
    private String description;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "user_id")
    private Long userId;

    @Builder.Default
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<File> files = new HashSet<>();
}
