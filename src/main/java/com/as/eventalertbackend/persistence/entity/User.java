package com.as.eventalertbackend.persistence.entity;

import com.as.eventalertbackend.persistence.entity.lookup.Gender;
import com.as.eventalertbackend.persistence.entity.lookup.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.as.eventalertbackend.AppConstants.*;

@Entity
@Table(name = "user", indexes ={@Index(name = "idx_email", columnList = "email")})
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime joinedAt;

    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = MAX_LENGTH_1000)
    private String password;

    @Column(length = MAX_LENGTH_50)
    private String firstName;

    @Column(length = MAX_LENGTH_50)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;

    private LocalDate dateOfBirth;

    @Column(length = MAX_LENGTH_15)
    private String phoneNumber;

    @Column(length = MAX_LENGTH_1000)
    private String imagePath;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Event> events;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Subscription> subscriptions;

    @Formula("(SELECT COUNT(e.id) FROM event e WHERE e.user_id = id)")
    private Integer reportsNumber;

    public User(String email, String password, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getId().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
