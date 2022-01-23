package jpa.board.controller;

import jpa.board.domain.Member;
import jpa.board.domain.dto.LoginDto;
import jpa.board.domain.dto.sessionname.SessionName;
import jpa.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpResponse;
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
    public String login(@Validated  @ModelAttribute LoginDto loginDto, BindingResult bindingResult, HttpServletRequest request) {
        if(bindingResult.hasErrors()) {

            return "/login";
        }

        Member login = memberService.login(loginDto.getLoginId(), loginDto.getPassword());

        if(login == null) {
            bindingResult.reject("login.fail");

            return "/login";
        }

        makeSession(request, login);

        log.info("Login ID : {}, Password : {}, LoginTime : {}", loginDto.getLoginId(), loginDto.getPassword(), LocalDateTime.now());

        return "redirect:/";
    }

    private void makeSession(HttpServletRequest request, Member login) {
        HttpSession session = request.getSession(true);
        session.setAttribute(SESSION_ID, login.getId());

        log.info("session : {}", session.getAttribute(SESSION_ID));
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
