package com.as.eventalertbackend.persistence.entity;

import com.as.eventalertbackend.enums.id.OrderId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static com.as.eventalertbackend.AppConstants.LENGTH_1000;
import static com.as.eventalertbackend.AppConstants.LENGTH_50;

@Entity
@Table(name = "ref_order") // Lookup table
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @Column(length = LENGTH_50)
    @Enumerated(EnumType.STRING)
    private OrderId id;

    @Column(nullable = false, length = LENGTH_50)
    private String label;

    @Column(nullable = false, length = LENGTH_1000)
    private String imagePath;

    @Column(nullable = false, unique = true)
    private Integer position;

}
