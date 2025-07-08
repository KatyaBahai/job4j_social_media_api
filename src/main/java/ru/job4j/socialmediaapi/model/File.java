package ru.job4j.socialmediaapi.model;

import lombok.*;

import javax.persistence.*;

@Entity
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
    private int id;
    private String name;
    private String path;
    @Column(name = "post_id")
    private int postId;
}
