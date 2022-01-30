package jpa.board.controller;

import jpa.board.domain.Member;
import jpa.board.domain.dto.*;
import jpa.board.exception.DuplicatedException;
import jpa.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
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

        try{
            memberService.joinMember(newMember);
        } catch(DuplicatedException e) {
            log.error("중복되는 로그인 아이디 : {}", newMember.getLoginId());
            bindingResult.rejectValue("loginId", "DuplicatedLoginId");
            return "/member/memberJoinForm";
        } catch(DuplicateKeyException e) {
            log.error("기본 키 중복 : {}", newMember.getId());
            bindingResult.reject("DuplicateMember");
            return "redirect:/error";
        }

        return "redirect:/login?redirectURL=/";
    }

    @GetMapping("/lost-id")
    public String lostIdForm(Model model) {
        model.addAttribute("findLoginIdDto", new FindLoginIdDto());

        return "/member/lostIdForm";
    }

    @PostMapping("/lost-id")
    public String findLostId(@Validated @ModelAttribute FindLoginIdDto findLoginIdDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getGlobalErrors();

            allErrors.forEach(error -> log.error("Error Arguments : {}, Error Message : {}", error.getDefaultMessage()));

            return "/member/lostIdForm";
        }

        String findLoginId = memberService.findLoginId(findLoginIdDto);

        if(findLoginId == null) {
            bindingResult.reject("NotExist.member");
            return "/member/lostIdForm";
        }

        redirectAttributes.addAttribute("findLoginId", findLoginId);

        return "redirect:/members/lost-id/result";
    }

    @GetMapping("/lost-id/result")
    public String lostIdResult(@RequestParam(required = true) String findLoginId, Model model) {
        model.addAttribute("findLoginId", findLoginId);

        return "/member/lostIdResult";
    }

    @GetMapping("/lost-password")
    public String lostPasswordForm(Model model) {
        model.addAttribute("findPasswordDto", new FindPasswordDto());

        return "/member/lostPasswordForm";
    }

    @PostMapping("/lost-password")
    public String findLostPassword(@Validated @ModelAttribute FindPasswordDto findPasswordDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getGlobalErrors();
            allErrors.forEach(error -> log.error("Error Arguments : {}, Error Message : {}", error.getDefaultMessage()));
            return "/member/lostPasswordForm";
        }

        Member findMember = memberService.findByIdAndEmail(findPasswordDto);

        if(findMember == null) {
            bindingResult.reject("NotExist.member");
            return "/member/lostPasswordForm";
        }

        redirectAttributes.addAttribute("memberId", findMember.getId());

        return "redirect:/members/{memberId}/new-password";
    }

    @GetMapping("/{memberId}/new-password")
    public String newPasswordForm(@PathVariable Long memberId, Model model) {
        model.addAttribute("newPasswordDto", new NewPasswordDto());

        return "/member/newPasswordForm";
    }

    @PostMapping("/{memberId}/new-password")
    public String newPasswordForm(@PathVariable Long memberId, @Validated @ModelAttribute NewPasswordDto newPasswordDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getGlobalErrors();
            allErrors.forEach(error -> log.error("Error Arguments : {}, Error Message : {}", error.getDefaultMessage()));
            return "/member/newPasswordForm";
        }

        try {
            memberService.newPassword(memberId, newPasswordDto);
        } catch(IllegalArgumentException e) {
            log.error("두 비밀번호가 일치하지 않음.");
            bindingResult.reject("NotMatchPassword");

            return "/member/newPasswordForm";
        } catch(DuplicatedException e) {
            log.error("이전 비밀번호와 같음.");
            bindingResult.reject("DuplicatedPassword");

            return "/member/newPasswordForm";
        }

        return "redirect:/login?redirectURL=/boards";
    }

    @GetMapping("/{memberId}/info")
    public String getMemberInfo(@PathVariable Long memberId, Model model) {
        Member member = memberService.findMember(memberId);
        model.addAttribute("member", member);

        return "/member/memberInfo";
    }

    @GetMapping("/{memberId}/edit")
    public String editMemberForm(@PathVariable Long memberId, Model model) {
        Member member = memberService.findMember(memberId);
        MemberEditDto memberEditDto = getMemberEditDto(member);
        model.addAttribute("memberEditDto", memberEditDto);
        model.addAttribute("memberId", memberId);

        return "/member/memberEditForm";
    }

    @PostMapping("/{memberId}/edit")
    public String editMemberForm(@PathVariable Long memberId, @Validated @ModelAttribute MemberEditDto memberEditDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getGlobalErrors();
            allErrors.forEach(error -> log.error("Error Arguments : {}, Error Message : {}", error.getDefaultMessage()));

            return "/member/memberEditForm";
        }

        memberService.editMember(memberId, memberEditDto);
        redirectAttributes.addAttribute("memberId", memberId);

        return "redirect:/members/{memberId}/info";
    }

    private MemberEditDto getMemberEditDto(Member member) {
        MemberEditDto memberEditDto = new MemberEditDto();
        return memberEditDto.convertToMemberEditDto(member);
    }

    @GetMapping("/{memberId}/delete")
    public String deleteMemberForm(@PathVariable Long memberId, Model model) {
        model.addAttribute("memberDeleteDto", new MemberDeleteDto());
        model.addAttribute("memberId", memberId);

        return "/member/memberDeleteForm";
    }

    @PostMapping("/{memberId}/delete")
    public String deleteMember(@PathVariable Long memberId, @Validated @ModelAttribute MemberDeleteDto memberDeleteDto, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getGlobalErrors();
            allErrors.forEach(error -> log.error("Error Arguments : {}, Error Message : {}", error.getDefaultMessage()));

            return "/member/memberDeleteForm";
        }

        try {
            memberService.deleteMember(memberId, memberDeleteDto);

            HttpSession httpSession = request.getSession(false);
            httpSession.invalidate();
        } catch(IllegalArgumentException e) {
            log.error("두 비밀번호가 일치하지 않음.");
            bindingResult.reject("NotMatchPassword");

            return "/member/memberDeleteForm";
        } catch(IllegalAccessException e) {
            log.error("회원 정보의 비밀번호가 일치하지 않음.");
            bindingResult.reject("NotEqualPassword");

            return "/member/memberDeleteForm";
        }

        return "redirect:/members/deletion-result";
    }

    @GetMapping("/deletion-result")
    public String deletionResult() {
        return "member/deletionResult";
    }
}
