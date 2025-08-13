package ru.job4j.socialmediaapi.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.*;
import ru.job4j.socialmediaapi.validation.Operations;

@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @NotNull(message = "Id can't be null", groups = {
            Operations.OnDelete.class,
            Operations.OnUpdate.class
    })
    private Long id;
    private String name;
    private String email;
    private String password;
}
