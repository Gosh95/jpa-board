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

    Optional<Member> findMemberByLoginIdAndEmail(String loginId, String email);

    @Query("select m.loginId from Member m where m.name = :name and m.email = :email")
    String findLoginId(String name, String email);

    @Query("select m from Member m where m.loginId = :loginId and m.password = :password")
    Optional<Member> findMemberByLoginIdAndPassword(String loginId, String password);
}
