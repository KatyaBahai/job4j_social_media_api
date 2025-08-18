package ru.job4j.socialmediaapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "User Model Information")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @NotNull(message = "Id can't be null", groups = {
            Operations.OnDelete.class,
            Operations.OnUpdate.class
    })
    private Long id;
    @Schema(description = "User name", example = "Mediator")
    private String name;
    private String email;
    private String password;
}
