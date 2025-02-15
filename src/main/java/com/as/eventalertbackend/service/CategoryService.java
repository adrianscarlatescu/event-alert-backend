package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.response.CategoryResponse;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.persistence.entity.Category;
import com.as.eventalertbackend.persistence.reopsitory.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    private final ModelMapper mapper;

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(ModelMapper mapper,
                           CategoryRepository categoryRepository) {
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    Category findEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.CATEGORY_NOT_FOUND));
    }

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(category -> mapper.map(category, CategoryResponse.class))
                .collect(Collectors.toList());
    }

    public CategoryResponse findById(Long id) {
        return mapper.map(findEntityById(id), CategoryResponse.class);
    }

    /*public EventSeverity save(EventSeverityUpdateRequest severityRequest) {
        return severityRepository.save(createOrUpdate(new EventSeverity(), severityRequest));
    }

    public EventSeverity updateById(EventSeverityUpdateRequest severityRequest, Long id) {
        return createOrUpdate(findEntityById(id), severityRequest);
    }*/

    public void deleteById(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.CATEGORY_NOT_FOUND);
        }
    }

    /*private EventSeverity createOrUpdate(EventSeverity severity, EventSeverityUpdateRequest severityRequest) {
        severity.setName(severityRequest.getName());
        severity.setColor(severityRequest.getColor());

        return severity;
    }*/

}
