package io.github.floreo1242.deas.controller;

import io.github.floreo1242.deas.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final EventService eventService;

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("createdEvents", eventService.getEventByCreator(auth.getName()));
        model.addAttribute("appliedEvents", eventService.getEventByApply(auth.getName()));
        return "profile";
    }
}
