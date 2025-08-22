package ru.job4j.socialmediaapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.persistence.*;
import ru.job4j.socialmediaapi.security.model.Role;
import ru.job4j.socialmediaapi.validation.Operations;

import java.util.HashSet;
import java.util.Set;

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
    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
