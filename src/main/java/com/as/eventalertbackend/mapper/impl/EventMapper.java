package com.as.eventalertbackend.mapper.impl;

import com.as.eventalertbackend.dto.response.EventDto;
import com.as.eventalertbackend.jpa.entity.Event;
import com.as.eventalertbackend.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventMapper implements Mapper<Event, EventDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public EventMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public EventDto mapTo(Event event) {
        return modelMapper.map(event, EventDto.class);
    }

    @Override
    public Event mapFrom(EventDto eventDto) {
        return modelMapper.map(eventDto, Event.class);
    }

}
