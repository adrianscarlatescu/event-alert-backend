package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.category.CategoryBaseDTO;
import com.as.eventalertbackend.dto.category.CategoryCreateDTO;
import com.as.eventalertbackend.dto.category.CategoryDTO;
import com.as.eventalertbackend.dto.category.CategoryUpdateDTO;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
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

    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(category -> mapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    public CategoryDTO findById(Long id) {
        return mapper.map(findEntityById(id), CategoryDTO.class);
    }

    public CategoryBaseDTO save(CategoryCreateDTO categoryCreateDTO) {
        Category category = new Category();

        if (categoryRepository.existsByCode(categoryCreateDTO.getCode())) {
            throw new InvalidActionException(ApiErrorMessage.CATEGORY_EXISTS);
        }

        category.setCode(categoryCreateDTO.getCode());
        category.setLabel(categoryCreateDTO.getLabel());
        category.setImagePath(categoryCreateDTO.getImagePath());

        return mapper.map(categoryRepository.save(category), CategoryBaseDTO.class);
    }

    public CategoryBaseDTO updateById(CategoryUpdateDTO categoryUpdateDTO, Long id) {
        Category category = findEntityById(id);

        if (categoryUpdateDTO.getCode() != null) {
            if (categoryRepository.existsByCode(categoryUpdateDTO.getCode())) {
                throw new InvalidActionException(ApiErrorMessage.CATEGORY_EXISTS);
            }
            category.setCode(categoryUpdateDTO.getCode());
        }
        if (categoryUpdateDTO.getLabel() != null) {
            category.setLabel(categoryUpdateDTO.getLabel());
        }
        if (categoryUpdateDTO.getImagePath() != null) {
            category.setImagePath(categoryUpdateDTO.getImagePath());
        }

        return mapper.map(category, CategoryBaseDTO.class);
    }

    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RecordNotFoundException(ApiErrorMessage.CATEGORY_NOT_FOUND);
        }
        if (categoryRepository.existsTypeByCategoryId(id)) {
            throw new InvalidActionException(ApiErrorMessage.SEVERITY_REFERENCED);
        }
        categoryRepository.deleteById(id);
    }

}
