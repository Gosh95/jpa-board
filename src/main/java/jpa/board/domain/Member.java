package jpa.board.domain;

import jpa.board.domain.dto.MemberJoinDto;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Member extends TimeEntity{
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;
    private String loginId;
    private String password;
    private String email;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @Embedded
    private Address address;

    public Member joinMember(MemberJoinDto memberJoinDto) {
        this.name = memberJoinDto.getName();
        this.loginId = memberJoinDto.getLoginId();
        this.password = memberJoinDto.getPassword();
        this.email = memberJoinDto.getEmail();
        this.address = new Address(memberJoinDto.getCountry(), memberJoinDto.getCity(), memberJoinDto.getZipcode());

        return this;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
