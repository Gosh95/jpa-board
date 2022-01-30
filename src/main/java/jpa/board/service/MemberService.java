package jpa.board.service;

import jpa.board.domain.Member;
import jpa.board.domain.dto.*;
import jpa.board.exception.DuplicatedException;
import jpa.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long joinMember(Member member) {
        Member checkedMember = checkMember(member);

        Member savedMember = memberRepository.save(checkedMember);

        return savedMember.getId();
    }

    public Member findMember(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    private Member checkMember(Member member) {
        List<Member> membersById = memberRepository.findMembersById(member.getId());
        List<Member> membersByLoginId = memberRepository.findMembersByLoginId(member.getLoginId());

        if(!membersById.isEmpty()) {
            throw new DuplicateKeyException("중복되는 회원입니다.");
        }

        if(!membersByLoginId.isEmpty()) {
            throw new DuplicatedException();
        }

        return member;
    }

    public Member login(String loginId, String password) {
        Optional<Member> member = memberRepository.findMemberByLoginIdAndPassword(loginId, password);

        return member.orElse(null);
    }

    public String findLoginId(FindLoginIdDto findLoginIdDto) {
        return memberRepository.findLoginId(findLoginIdDto.getName(), findLoginIdDto.getEmail());
    }

    public Member findByIdAndEmail(FindPasswordDto findPasswordDto) {
        return memberRepository.findMemberByLoginIdAndEmail(findPasswordDto.getLoginId(), findPasswordDto.getEmail()).orElse(null);
    }

    @Transactional
    public void newPassword(Long memberId, NewPasswordDto newPasswordDto) {
        String newPassword = newPasswordDto.getNewPassword();

        checkPassword(newPassword, newPasswordDto.getCheckPassword());

        Optional<Member> opMem = memberRepository.findById(memberId);
        Member member = opMem.orElse(null);
        if(newPassword.equals(member.getPassword())) throw new DuplicatedException();

        member.changePassword(newPassword);
    }

    @Transactional
    public Member editMember(Long memberId, MemberEditDto memberEditDto) {
        Optional<Member> opMem = memberRepository.findById(memberId);
        Member member = opMem.orElse(null);
        member.editMember(memberEditDto);
        return member;
    }

    @Transactional
    public void deleteMember(Long memberId, MemberDeleteDto memberDeleteDto) throws IllegalAccessException {
        String password = memberDeleteDto.getPassword();

        checkPassword(password, memberDeleteDto.getCheckPassword());

        Optional<Member> opMem = memberRepository.findById(memberId);
        Member member = opMem.orElse(null);

        if(!member.getPassword().equals(password)) {
            throw new IllegalAccessException();
        }

        memberRepository.delete(member);
    }

    private void checkPassword(String password, String checkPassword) {
        if(!password.equals(checkPassword)) {
            throw new IllegalArgumentException();
        }
    }
}
