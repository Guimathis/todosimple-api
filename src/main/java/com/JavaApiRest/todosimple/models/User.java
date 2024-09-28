package com.JavaApiRest.todosimple.models;

import com.JavaApiRest.todosimple.models.Enums.ProfileEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = User.TABLE_NAME)
public class User {
    protected static final String TABLE_NAME = "user";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    @NotBlank(message = "Username não pode estar vazio.")
    @Size(min = 2, max = 100)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false, unique = true)
    @NotBlank(message = "Password não pode estar vazio.")
    @Size(min = 8)
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Task> tasks = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CollectionTable(name = "user_profile")
    @Column(name = "profile", nullable = false)
    private Set<Integer> profiles = new HashSet<>();

    public Set<ProfileEnum> getProfiles() {
        return this.profiles.stream().map(x -> ProfileEnum.toEnum(x)).collect(Collectors.toSet());
    }

    public void addProfile(ProfileEnum profileEnum) {
        this.profiles.add(profileEnum.getCode());
    }
}
