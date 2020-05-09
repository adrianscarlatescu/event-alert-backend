package com.as.eventalertbackend.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedResponse<T> {

    private int totalPages;
    private long totalElements;
    private List<T> content;

}
