package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.order.OrderDTO;
import com.as.eventalertbackend.persistence.reopsitory.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final ModelMapper mapper;

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(ModelMapper mapper,
                        OrderRepository orderRepository) {
        this.mapper = mapper;
        this.orderRepository = orderRepository;
    }

    public List<OrderDTO> findAll() {
        return orderRepository.findAllByOrderByPositionAsc().stream()
                .map((element) -> mapper.map(element, OrderDTO.class))
                .collect(Collectors.toList());
    }

}
