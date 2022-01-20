package jpa.board.controller;

import jpa.board.domain.Member;
import jpa.board.domain.dto.MemberJoinDto;
import jpa.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/new")
    public String joinMemberForm(Model model) {
        model.addAttribute("memberJoinDto", new MemberJoinDto());

        return "/member/memberJoinForm";
    }

    @PostMapping("/new")
    public String joinMember(@Validated @ModelAttribute MemberJoinDto memberJoinDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getGlobalErrors();

            allErrors.forEach(error -> log.error("Error Arguments : {}, Error Message : {}", error.getDefaultMessage()));

            return "/member/memberJoinForm";
        }

        Member member = new Member();
        Member newMember = member.joinMember(memberJoinDto);

        memberService.joinMember(newMember);

        return "redirect:/";
    }

}
