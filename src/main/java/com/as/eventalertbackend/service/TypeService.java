package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.response.TypeResponse;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
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

    @Autowired
    public TypeService(ModelMapper mapper,
                       TypeRepository typeRepository,
                       FileService fileService) {
        this.mapper = mapper;
        this.typeRepository = typeRepository;
        this.fileService = fileService;
    }

    Type findEntityById(Long id) {
        return typeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.TYPE_NOT_FOUND));
    }

    public List<TypeResponse> findAll() {
        return typeRepository.findAll().stream()
                .map(tag -> mapper.map(tag, TypeResponse.class))
                .collect(Collectors.toList());
    }

    public TypeResponse findById(Long id) {
        return mapper.map(findEntityById(id), TypeResponse.class);
    }

    /*public EventType save(EventTypeUpdateRequest tagRequest) {
        return typeRepository.save(createOrUpdate(new EventType(), tagRequest));
    }

    public EventType updateById(EventTypeUpdateRequest tagRequest, Long id) {
        return createOrUpdate(findEntityById(id), tagRequest);
    }*/

    public void deleteById(Long id) {
        if (typeRepository.existsById(id)) {
            typeRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.TYPE_NOT_FOUND);
        }
    }

    /*private EventType createOrUpdate(EventType tag, EventTypeUpdateRequest tagRequest) {
        if (!fileService.imageExists(tagRequest.getImagePath())) {
            throw new ResourceNotFoundException(ApiErrorMessage.IMAGE_NOT_FOUND);
        }

        tag.setCode(tagRequest.getCode());
        tag.setLabel(tagRequest.getLabel());
        tag.setImagePath(tagRequest.getImagePath());

        return tag;
    }*/

}
