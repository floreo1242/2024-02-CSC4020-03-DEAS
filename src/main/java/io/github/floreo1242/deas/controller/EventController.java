package io.github.floreo1242.deas.controller;

import io.github.floreo1242.deas.DTO.request.CreateEventRequest;
import io.github.floreo1242.deas.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("/event")
    public String createEvent(@ModelAttribute CreateEventRequest request) {
        boolean isCreated = eventService.createEvent(request);
        if (!isCreated) {
            return "redirect:/event/create";
        }
        return "redirect:/event";
    }
}
