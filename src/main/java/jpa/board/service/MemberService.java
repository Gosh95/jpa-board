package jpa.board.service;

import jpa.board.domain.Member;
import jpa.board.exception.DuplicatedException;
import jpa.board.exception.NotExistException;
import jpa.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long joinMember(Member member) {
        Member checkedMember = checkMember(member);

        Member savedMember = memberRepository.save(checkedMember);

        return savedMember.getId();
    }

    private Member checkMember(Member member) {
        List<Member> membersById = memberRepository.findMembersById(member.getId());
        List<Member> membersByLoginId = memberRepository.findMembersByLoginId(member.getLoginId());

        if(!membersById.isEmpty()) {
            throw new DuplicatedException("중복되는 회원입니다.");
        }

        return member;
    }

    public Member login(String loginId, String password) {
        Optional<Member> member = memberRepository.findMemberByLoginIdAndPassword(loginId, password);

        return member.orElse(null);
    }
}
