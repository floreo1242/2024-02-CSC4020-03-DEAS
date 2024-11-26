package io.github.floreo1242.deas.controller;

import io.github.floreo1242.deas.domain.Event;
import io.github.floreo1242.deas.service.CustomUserDetails;
import io.github.floreo1242.deas.service.EventService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class WebController {

    private final EventService eventService;

    public WebController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/home")
    public String home(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            session.setAttribute("id", auth.getName());
            session.setAttribute("name", userDetails.getName());
        }
        return "home";
    }

    @GetMapping("/event/create")
    public String createEvent() {
        return "create-event";
    }

    @GetMapping("/event")
    public String event(Model model) {
        model.addAttribute("events", eventService.getEventList());
        return "event";
    }

    @GetMapping("/event/{eventId}")
    public String event(@PathVariable Integer eventId, Model model) {
        model.addAttribute("event", eventService.getEventById(eventId));
        return "event-detail";
    }
}
