package io.github.floreo1242.deas.controller;

import io.github.floreo1242.deas.DTO.request.ApplyEventRequest;
import io.github.floreo1242.deas.DTO.request.CreateEventRequest;
import io.github.floreo1242.deas.service.ApplyService;
import io.github.floreo1242.deas.service.EventService;
import io.github.floreo1242.deas.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final QuestionService questionService;
    private final ApplyService applyService;

    @GetMapping("/event")
    public String event(Model model) {
        model.addAttribute("events", eventService.getEventList());
        return "event";
    }

    @PostMapping("/event")
    public String createEvent(@RequestBody CreateEventRequest request) {
        boolean isCreated = eventService.createEvent(request);
        if (!isCreated) {
            return "redirect:/event/create";
        }
        return "redirect:/event";
    }

    @GetMapping("/event/create")
    public String createEvent() {
        return "create-event";
    }

    @GetMapping("/event/{eventId}")
    public String event(@PathVariable Integer eventId, Model model) {
        model.addAttribute("event", eventService.getEventById(eventId));
        return "event-detail";
    }

    @GetMapping("/event/apply/{eventId}")
    public String apply(@PathVariable Integer eventId, Model model) {
        model.addAttribute("eventId", eventId);
        model.addAttribute("questions", questionService.getQuestions(eventId));
        return "event-apply";
    }

    @PostMapping("/event/apply")
    public String applyEvent(@ModelAttribute ApplyEventRequest request) {
        boolean isApplied = eventService.applyEvent(request);
        if (!isApplied) {
            return "redirect:/event/" + request.getEventId();
        }
        return "redirect:/event";
    }

    @GetMapping("/event/{eventId}/applications")
    public String getApply(@PathVariable Integer eventId, Model model) {
        model.addAttribute("applyDetailResponse", applyService.getApplyDetails(eventId));
        return "apply-detail";
    }
}
