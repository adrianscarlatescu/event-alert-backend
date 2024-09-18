package com.as.eventalertbackend.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<A, B> {

    B mapTo(A a);

    default List<B> mapTo(List<A> list) {
        return list.stream().map(this::mapTo).collect(Collectors.toList());
    }

    A mapFrom(B b);

    default List<A> mapFrom(List<B> list) {
        return list.stream().map(this::mapFrom).collect(Collectors.toList());
    }

}
