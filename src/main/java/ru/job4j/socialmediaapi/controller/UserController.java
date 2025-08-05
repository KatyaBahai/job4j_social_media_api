package ru.job4j.socialmediaapi.controller;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmediaapi.model.User;
import ru.job4j.socialmediaapi.service.user.UserService;

import java.util.List;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> get(@PathVariable("userId")
                                    @NotNull
                                    @Min(value = 1, message = "User id minimal value is 1")
                                            Long userId) {
        return userService.findById(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        userService.save(user);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> update(@RequestBody User user,
                       @PathVariable("userId")
                       @NotNull
                       @Min(value = 1, message = "User id minimal value is 1")
                               Long userId) {

        if (userService.update(userId, user).isPresent()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeById(@PathVariable long userId) {
        if (userService.deleteById(userId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findAll());
    }

}
