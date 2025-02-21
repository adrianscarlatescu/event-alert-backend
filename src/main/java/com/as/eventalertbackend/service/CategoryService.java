package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.category.CategoryBaseDTO;
import com.as.eventalertbackend.dto.category.CategoryCreateDTO;
import com.as.eventalertbackend.dto.category.CategoryDTO;
import com.as.eventalertbackend.dto.category.CategoryUpdateDTO;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.error.exception.ResourceNotFoundException;
import com.as.eventalertbackend.persistence.entity.lookup.Category;
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

    private final FileService fileService;

    @Autowired
    public CategoryService(ModelMapper mapper,
                           CategoryRepository categoryRepository,
                           FileService fileService) {
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
        this.fileService = fileService;
    }

    Category findEntityById(String code) {
        return categoryRepository.findById(code)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.CATEGORY_NOT_FOUND));
    }

    public List<CategoryDTO> findAll() {
        return categoryRepository.findAllByOrderByLabelAsc().stream()
                .map(category -> mapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    public CategoryDTO findById(String code) {
        return mapper.map(findEntityById(code), CategoryDTO.class);
    }

    public CategoryBaseDTO save(CategoryCreateDTO categoryCreateDTO) {
        Category category = new Category();

        if (categoryRepository.existsById(categoryCreateDTO.getId())) {
            throw new InvalidActionException(ApiErrorMessage.CATEGORY_ID_EXISTS);
        }
        if (categoryRepository.existsByPosition(categoryCreateDTO.getPosition())) {
            throw new InvalidActionException(ApiErrorMessage.CATEGORY_POSITION_EXISTS);
        }
        if (!fileService.imageExists(categoryCreateDTO.getImagePath())) {
            throw new ResourceNotFoundException(ApiErrorMessage.IMAGE_NOT_FOUND);
        }

        category.setId(categoryCreateDTO.getId());
        category.setLabel(categoryCreateDTO.getLabel());
        category.setImagePath(categoryCreateDTO.getImagePath());
        category.setPosition(categoryCreateDTO.getPosition());

        return mapper.map(categoryRepository.save(category), CategoryBaseDTO.class);
    }

    public CategoryBaseDTO updateById(CategoryUpdateDTO categoryUpdateDTO, String code) {
        Category category = findEntityById(code);

        if (categoryRepository.existsByPosition(categoryUpdateDTO.getPosition())) {
            throw new InvalidActionException(ApiErrorMessage.CATEGORY_POSITION_EXISTS);
        }
        if (!fileService.imageExists(categoryUpdateDTO.getImagePath())) {
            throw new ResourceNotFoundException(ApiErrorMessage.IMAGE_NOT_FOUND);
        }

        category.setLabel(categoryUpdateDTO.getLabel());
        category.setImagePath(categoryUpdateDTO.getImagePath());
        category.setPosition(categoryUpdateDTO.getPosition());

        return mapper.map(category, CategoryBaseDTO.class);
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
