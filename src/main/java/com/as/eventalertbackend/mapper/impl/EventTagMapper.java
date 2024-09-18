package com.as.eventalertbackend.mapper.impl;

import com.as.eventalertbackend.dto.response.EventTagDto;
import com.as.eventalertbackend.jpa.entity.EventTag;
import com.as.eventalertbackend.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventTagMapper implements Mapper<EventTag, EventTagDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public EventTagMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public EventTagDto mapTo(EventTag tag) {
        return modelMapper.map(tag, EventTagDto.class);
    }

    @Override
    public EventTag mapFrom(EventTagDto tagDto) {
        return modelMapper.map(tagDto, EventTag.class);
    }

}
