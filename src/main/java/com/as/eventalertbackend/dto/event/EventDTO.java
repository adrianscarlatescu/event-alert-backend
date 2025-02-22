package com.as.eventalertbackend.dto.event;

import com.as.eventalertbackend.dto.severity.SeverityDTO;
import com.as.eventalertbackend.dto.status.StatusDTO;
import com.as.eventalertbackend.dto.type.TypeDTO;
import com.as.eventalertbackend.dto.user.UserBaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EventDTO implements Serializable {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Double latitude;
    private Double longitude;
    private BigDecimal impactRadius;
    private TypeDTO type;
    private SeverityDTO severity;
    private StatusDTO status;
    private UserBaseDTO user;
    private String imagePath;
    private String description;
    private Double distance;

}
