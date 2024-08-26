package com.JavaApiRest.todosimple.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = Task.TABLE_NAME)
public class Task {
    protected static final String TABLE_NAME = "task";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 1, max = 255)
    private String description;
}
