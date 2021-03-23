package com.as.eventalertbackend.data.model;

import com.as.eventalertbackend.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime joinDateTime;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String imagePath;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Event> events;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<EventComment> eventComments;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRole> userRoles;

    @Formula("(SELECT COUNT(e.id) FROM event e WHERE e.user_id = id)")
    private int reportsNumber;

    public User(String email, String password, Set<UserRole> userRoles) {
        this.email = email;
        this.password = password;
        this.userRoles = userRoles;
    }

}
