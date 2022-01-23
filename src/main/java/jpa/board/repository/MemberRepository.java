package jpa.board.repository;

import jpa.board.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findMembersById(Long id);
    List<Member> findMembersByLoginId(String loginId);

    @Query("select m from Member m where m.loginId = :loginId and m.password = :password")
    Optional<Member> findMemberByLoginIdAndPassword(String loginId, String password);
}
