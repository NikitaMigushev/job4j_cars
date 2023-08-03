package ru.job4j.cars.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "auto_user")
@Data
@JsonIgnoreProperties("posts")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @Column(name = "full_name")
    @EqualsAndHashCode.Include
    private String fullName;
    private String email;
    private String password;
    @OneToMany
    @JoinColumn(name = "auto_user_id")
    private Set<Post> posts = new HashSet<>();
}