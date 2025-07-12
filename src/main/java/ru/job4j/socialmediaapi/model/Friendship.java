package ru.job4j.socialmediaapi.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "friendships")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Friendship {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend1_id", nullable = false)
    private User friend1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend2_id", nullable = false)
    private User friend2;

    @Column(name = "friends_since")
    private Instant friendsSince;
}
