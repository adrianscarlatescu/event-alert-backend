package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.type.TypeCreateDTO;
import com.as.eventalertbackend.dto.type.TypeDTO;
import com.as.eventalertbackend.dto.type.TypeUpdateDTO;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.error.exception.ResourceNotFoundException;
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

    private final FileService fileService;
    private final CategoryService categoryService;

    @Autowired
    public TypeService(ModelMapper mapper,
                       TypeRepository typeRepository,
                       FileService fileService,
                       CategoryService categoryService) {
        this.mapper = mapper;
        this.typeRepository = typeRepository;
        this.fileService = fileService;
        this.categoryService = categoryService;
    }

    Type findEntityById(String id) {
        return typeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.TYPE_NOT_FOUND));
    }

    public List<TypeDTO> findAll() {
        return typeRepository.findAllByOrderByPositionAsc().stream()
                .map(tag -> mapper.map(tag, TypeDTO.class))
                .collect(Collectors.toList());
    }

    public TypeDTO findById(String id) {
        return mapper.map(findEntityById(id), TypeDTO.class);
    }

    public TypeDTO save(TypeCreateDTO typeCreateDTO) {
        Type type = new Type();

        if (typeRepository.existsById(typeCreateDTO.getId())) {
            throw new InvalidActionException(ApiErrorMessage.TYPE_ID_EXISTS);
        }
        if (typeRepository.existsByPosition(typeCreateDTO.getPosition())) {
            throw new InvalidActionException(ApiErrorMessage.TYPE_POSITION_EXISTS);
        }
        if (!fileService.imageExists(typeCreateDTO.getImagePath())) {
            throw new ResourceNotFoundException(ApiErrorMessage.IMAGE_NOT_FOUND);
        }

        type.setId(typeCreateDTO.getId());
        type.setLabel(typeCreateDTO.getLabel());
        type.setImagePath(typeCreateDTO.getImagePath());
        type.setPosition(typeCreateDTO.getPosition());

        Category category = categoryService.findEntityById(typeCreateDTO.getCategoryId());
        type.setCategory(category);

        return mapper.map(typeRepository.save(type), TypeDTO.class);
    }

    public TypeDTO updateById(TypeUpdateDTO typeUpdateDTO, String id) {
        Type type = findEntityById(id);

        if (typeRepository.existsByPositionAndIdIsNot(typeUpdateDTO.getPosition(), id)) {
            throw new InvalidActionException(ApiErrorMessage.TYPE_POSITION_EXISTS);
        }
        if (!fileService.imageExists(typeUpdateDTO.getImagePath())) {
            throw new ResourceNotFoundException(ApiErrorMessage.IMAGE_NOT_FOUND);
        }

        type.setLabel(typeUpdateDTO.getLabel());
        type.setImagePath(typeUpdateDTO.getImagePath());
        type.setPosition(typeUpdateDTO.getPosition());

        Category category = categoryService.findEntityById(typeUpdateDTO.getCategoryId());
        type.setCategory(category);

        return mapper.map(type, TypeDTO.class);
    }

    public void deleteById(String id) {
        if (!typeRepository.existsById(id)) {
            throw new RecordNotFoundException(ApiErrorMessage.TYPE_NOT_FOUND);
        }
        if (typeRepository.existsEventByTypeId(id)) {
            throw new InvalidActionException(ApiErrorMessage.TYPE_REFERENCED);
        }
        typeRepository.deleteById(id);
    }

}
