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
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody User user,
                       @PathVariable("userId")
                       @NotNull
                       @Min(value = 1, message = "User id minimal value is 1")
                               Long userId) {
        userService.update(userId, user);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeById(@PathVariable long userId) {
        userService.deleteById(userId);
    }
}
