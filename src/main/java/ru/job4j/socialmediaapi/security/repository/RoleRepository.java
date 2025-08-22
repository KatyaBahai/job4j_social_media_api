package ru.job4j.socialmediaapi.security.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmediaapi.security.model.ERole;
import ru.job4j.socialmediaapi.security.model.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
