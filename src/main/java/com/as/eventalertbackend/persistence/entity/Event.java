package com.as.eventalertbackend.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.as.eventalertbackend.AppConstants.MAX_LENGTH_1000;

@Entity
@Table(name = "event")
@Getter
@Setter
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(precision = 6, scale = 2)
    private BigDecimal impactRadius;

    @ManyToOne
    @JoinColumn(name = "severity_id", nullable = false)
    private Severity severity;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @OrderBy("createdAt desc")
    private List<Comment> comments;

    @Column(nullable = false, length = MAX_LENGTH_1000)
    private String imagePath;

    @Column(length = MAX_LENGTH_1000)
    private String description;

    @Transient
    private Double distance;

}
