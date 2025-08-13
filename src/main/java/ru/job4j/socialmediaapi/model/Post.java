package ru.job4j.socialmediaapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.persistence.*;
import ru.job4j.socialmediaapi.validation.Operations;

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
    @NotBlank
    private String description;

    @Builder.Default
    @Column(name = "creation_date", nullable = false, updatable = false)
    private Instant creationDate = Instant.now();

    @Column(name = "user_id")
    private Long userId;

    @Builder.Default
    @JsonManagedReference
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<File> files = new HashSet<>();
}
