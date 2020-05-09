package com.as.eventalertbackend.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tag")
@Getter
@Setter
public class EventTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3, max = 40, message = "The name must have between 3 and 40 characters")
    private String name;
    @NotEmpty(message = "Invalid image path")
    private String imagePath;

}
