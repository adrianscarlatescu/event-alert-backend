package com.as.eventalertbackend.mapper.impl;

import com.as.eventalertbackend.dto.response.SubscriptionDto;
import com.as.eventalertbackend.jpa.entity.Subscription;
import com.as.eventalertbackend.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper implements Mapper<Subscription, SubscriptionDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public SubscriptionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public SubscriptionDto mapTo(Subscription subscription) {
        return modelMapper.map(subscription, SubscriptionDto.class);
    }

    @Override
    public Subscription mapFrom(SubscriptionDto subscriptionDto) {
        return modelMapper.map(subscriptionDto, Subscription.class);
    }

}
