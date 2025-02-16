package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.type.TypeCreateDTO;
import com.as.eventalertbackend.dto.type.TypeDTO;
import com.as.eventalertbackend.dto.type.TypeUpdateDTO;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.persistence.entity.Category;
import com.as.eventalertbackend.persistence.entity.Type;
import com.as.eventalertbackend.persistence.reopsitory.TypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TypeService {

    private final ModelMapper mapper;

    private final TypeRepository typeRepository;

    private final CategoryService categoryService;

    @Autowired
    public TypeService(ModelMapper mapper,
                       TypeRepository typeRepository,
                       CategoryService categoryService) {
        this.mapper = mapper;
        this.typeRepository = typeRepository;
        this.categoryService = categoryService;
    }

    Type findEntityById(Long id) {
        return typeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.TYPE_NOT_FOUND));
    }

    public List<TypeDTO> findAll() {
        return typeRepository.findAll().stream()
                .map(tag -> mapper.map(tag, TypeDTO.class))
                .collect(Collectors.toList());
    }

    public TypeDTO findById(Long id) {
        return mapper.map(findEntityById(id), TypeDTO.class);
    }

    public TypeDTO save(TypeCreateDTO typeCreateDTO) {
        Type type = new Type();

        if (typeRepository.existsByCode(typeCreateDTO.getCode())) {
            throw new InvalidActionException(ApiErrorMessage.TYPE_EXISTS);
        }

        Category category = categoryService.findEntityById(typeCreateDTO.getCategoryId());

        type.setCategory(category);
        type.setCode(typeCreateDTO.getCode());
        type.setLabel(typeCreateDTO.getLabel());
        type.setImagePath(typeCreateDTO.getImagePath());

        return mapper.map(typeRepository.save(type), TypeDTO.class);
    }

    public TypeDTO updateById(TypeUpdateDTO typeUpdateDTO, Long id) {
        Type type = findEntityById(id);

        if (typeUpdateDTO.getCategoryId() != null) {
            Category category = categoryService.findEntityById(typeUpdateDTO.getCategoryId());
            type.setCategory(category);
        }
        if (type.getCode() != null) {
            if (typeRepository.existsByCode(typeUpdateDTO.getCode())) {
                throw new InvalidActionException(ApiErrorMessage.TYPE_EXISTS);
            }
            type.setCode(typeUpdateDTO.getCode());
        }
        if (typeUpdateDTO.getLabel() != null) {
            type.setLabel(typeUpdateDTO.getLabel());
        }
        if (typeUpdateDTO.getImagePath() != null) {
            type.setImagePath(typeUpdateDTO.getImagePath());
        }

        return mapper.map(type, TypeDTO.class);
    }

    public void deleteById(Long id) {
        if (!typeRepository.existsById(id)) {
            throw new RecordNotFoundException(ApiErrorMessage.TYPE_NOT_FOUND);
        }
        if (typeRepository.existsEventByTypeId(id)) {
            throw new InvalidActionException(ApiErrorMessage.TYPE_REFERENCED);
        }
        typeRepository.deleteById(id);
    }

}
