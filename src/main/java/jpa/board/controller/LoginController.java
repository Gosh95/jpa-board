package jpa.board.controller;

import jpa.board.domain.Member;
import jpa.board.domain.dto.LoginDto;
import jpa.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

import static jpa.board.domain.dto.sessionname.SessionName.SESSION_ID;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginDto", new LoginDto());

        return "/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginDto loginDto, BindingResult bindingResult,
                        @RequestParam(name = "redirectURL", defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            return "/login";
        }

        Member member = memberService.login(loginDto.getLoginId(), loginDto.getPassword());

        if(member == null) {
            bindingResult.reject("login.fail");

            return "/login";
        }

        makeSession(request, member);
        log.info("Login ID : {}, Password : {}, LoginTime : {}", loginDto.getLoginId(), loginDto.getPassword(), LocalDateTime.now());

        return "redirect:" + redirectURL;
    }

    private void makeSession(HttpServletRequest request, Member member) {
        HttpSession session = request.getSession(true);
        session.setAttribute(SESSION_ID, member.getId());
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if(session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }
}
