package com.as.eventalertbackend.data.model;

import com.as.eventalertbackend.dto.UserDto;
import com.as.eventalertbackend.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class User implements UserDetails {

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

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Subscription> subscriptions;

    @Formula("(SELECT COUNT(e.id) FROM event e WHERE e.user_id = id)")
    private int reportsNumber;

    public User(String email, String password, Set<UserRole> userRoles) {
        this.email = email;
        this.password = password;
        this.userRoles = userRoles;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toSet());
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserDto toDto() {
        UserDto dto = new UserDto();
        dto.setId(getId());
        dto.setJoinDateTime(getJoinDateTime());
        dto.setEmail(getEmail());
        dto.setFirstName(getFirstName());
        dto.setLastName(getLastName());
        dto.setDateOfBirth(getDateOfBirth());
        dto.setPhoneNumber(getPhoneNumber());
        dto.setImagePath(getImagePath());
        dto.setGender(getGender());
        dto.setUserRoles(getUserRoles() == null ?
                null : getUserRoles().stream().map(UserRole::toDto).collect(Collectors.toSet()));
        dto.setReportsNumber(getReportsNumber());
        return dto;
    }

}
