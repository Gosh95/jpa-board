package jpa.board.service;

import jpa.board.domain.Member;
import jpa.board.domain.dto.FindLoginIdDto;
import jpa.board.domain.dto.FindPasswordDto;
import jpa.board.domain.dto.NewPasswordDto;
import jpa.board.exception.DuplicatedException;
import jpa.board.exception.NotExistException;
import jpa.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
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

        if(!membersById.isEmpty()) {
            throw new DuplicatedException("중복되는 회원입니다.");
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

        if(!newPassword.equals(newPasswordDto.getCheckPassword())) {
            throw new IllegalArgumentException();
        }

        Optional<Member> opMem = memberRepository.findById(memberId);
        Member member = opMem.get();
        if(newPassword.equals(member.getPassword())) throw new DuplicatedException();

        member.changePassword(newPassword);
    }
}
