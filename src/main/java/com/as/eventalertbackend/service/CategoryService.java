package com.as.eventalertbackend.service;

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

    Category findEntityById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.CATEGORY_NOT_FOUND));
    }

    public List<CategoryDTO> findAll() {
        return categoryRepository.findAllByOrderByLabelAsc().stream()
                .map(category -> mapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    public CategoryDTO findById(String id) {
        return mapper.map(findEntityById(id), CategoryDTO.class);
    }

    public CategoryDTO save(CategoryCreateDTO categoryCreateDTO) {
        Category category = new Category();

        if (categoryRepository.existsById(categoryCreateDTO.getId())) {
            throw new InvalidActionException(ApiErrorMessage.CATEGORY_ID_EXISTS);
        }
        if (categoryRepository.existsByPosition(categoryCreateDTO.getPosition())) {
            throw new InvalidActionException(ApiErrorMessage.CATEGORY_POSITION_EXISTS);
        }

        category.setId(categoryCreateDTO.getId());
        category.setLabel(categoryCreateDTO.getLabel());
        category.setPosition(categoryCreateDTO.getPosition());

        return mapper.map(categoryRepository.save(category), CategoryDTO.class);
    }

    public CategoryDTO updateById(CategoryUpdateDTO categoryUpdateDTO, String id) {
        Category category = findEntityById(id);

        if (categoryRepository.existsByPositionAndIdIsNot(categoryUpdateDTO.getPosition(), id)) {
            throw new InvalidActionException(ApiErrorMessage.CATEGORY_POSITION_EXISTS);
        }

        category.setLabel(categoryUpdateDTO.getLabel());
        category.setPosition(categoryUpdateDTO.getPosition());

        return mapper.map(category, CategoryDTO.class);
    }

    public void deleteById(String id) {
        if (!categoryRepository.existsById(id)) {
            throw new RecordNotFoundException(ApiErrorMessage.CATEGORY_NOT_FOUND);
        }
        if (categoryRepository.existsTypeByCategoryCode(id)) {
            throw new InvalidActionException(ApiErrorMessage.SEVERITY_REFERENCED);
        }
        categoryRepository.deleteById(id);
    }

}
