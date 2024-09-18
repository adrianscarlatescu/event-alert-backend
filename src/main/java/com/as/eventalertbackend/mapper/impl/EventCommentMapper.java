package com.as.eventalertbackend.mapper.impl;

import com.as.eventalertbackend.dto.response.EventCommentDto;
import com.as.eventalertbackend.jpa.entity.EventComment;
import com.as.eventalertbackend.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventCommentMapper implements Mapper<EventComment, EventCommentDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public EventCommentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public EventCommentDto mapTo(EventComment comment) {
        return modelMapper.map(comment, EventCommentDto.class);
    }

    @Override
    public EventComment mapFrom(EventCommentDto commentDto) {
        return modelMapper.map(commentDto, EventComment.class);
    }

}
