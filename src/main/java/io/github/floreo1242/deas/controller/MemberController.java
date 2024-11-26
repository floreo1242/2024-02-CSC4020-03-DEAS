package io.github.floreo1242.deas.controller;

import io.github.floreo1242.deas.DTO.request.RegisterRequest;
import io.github.floreo1242.deas.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest request, Model model) {
        boolean isRegistered = memberService.register(request);
        if (!isRegistered) {
            model.addAttribute("error", "이미 등록된 사용자입니다.");
            return "register";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "사용자 이름 또는 비밀번호가 잘못되었습니다.");
        }
        if (logout != null) {
            model.addAttribute("logout", "로그아웃되었습니다.");
        }
        return "login";
    }
}
