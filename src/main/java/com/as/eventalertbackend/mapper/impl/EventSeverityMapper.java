package com.as.eventalertbackend.mapper.impl;

import com.as.eventalertbackend.dto.response.EventSeverityDto;
import com.as.eventalertbackend.jpa.entity.EventSeverity;
import com.as.eventalertbackend.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventSeverityMapper implements Mapper<EventSeverity, EventSeverityDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public EventSeverityMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public EventSeverityDto mapTo(EventSeverity severity) {
        return modelMapper.map(severity, EventSeverityDto.class);
    }

    @Override
    public EventSeverity mapFrom(EventSeverityDto severityDto) {
        return modelMapper.map(severityDto, EventSeverity.class);
    }

}
