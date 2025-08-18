package ru.job4j.socialmediaapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.persistence.*;
import java.time.Instant;

@Builder
@Entity
@Table(name = "friendships")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Schema(description = "Model representing 2 users who follow each other and see updates")
public class Friendship {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend1_id", nullable = false)
    private User friend1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend2_id", nullable = false)
    private User friend2;

    @Column(name = "friends_since")
    private Instant friendsSince;
}
