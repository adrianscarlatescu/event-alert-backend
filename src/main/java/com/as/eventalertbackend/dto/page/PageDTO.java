package com.as.eventalertbackend.dto.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> implements Serializable {

    private Integer totalPages;
    private Long totalElements;
    private List<T> content;

}
