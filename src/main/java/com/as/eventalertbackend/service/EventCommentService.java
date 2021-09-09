package com.as.eventalertbackend.service;

import com.as.eventalertbackend.controller.request.EventCommentBody;
import com.as.eventalertbackend.data.model.EventComment;
import com.as.eventalertbackend.data.reopsitory.EventCommentRepository;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventCommentService {

    private final EventCommentRepository repository;

    private final UserService userService;
    private final EventService eventService;

    @Autowired
    public EventCommentService(EventCommentRepository repository, UserService userService, EventService eventService) {
        this.repository = repository;
        this.userService = userService;
        this.eventService = eventService;
    }

    public EventComment findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        "No record for comment " + id,
                        "Comment not found"));
    }

    public List<EventComment> findByEventId(Long id) {
        return repository.findByEventIdOrderByDateTimeDesc(id);
    }

    public EventComment save(EventCommentBody body) {
        return update(new EventComment(), body);
    }

    public EventComment updateById(EventCommentBody body, Long id) {
        return update(findById(id), body);
    }

    public void deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException(
                    "No record for comment " + id,
                    "EventComment not found");
        }
    }

    private EventComment update(EventComment dbObj, EventCommentBody body) {
        dbObj.setEvent(eventService.findById(body.getEventId()));
        dbObj.setUser(userService.findById(body.getUserId()));
        dbObj.setComment(body.getComment());
        return repository.save(dbObj);
    }

}
