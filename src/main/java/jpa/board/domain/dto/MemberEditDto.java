package jpa.board.domain.dto;

import jpa.board.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEditDto {
    //영어 + 문자 + 특수문자 하나이상
    @NotBlank @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$")
    private String password;

    @NotBlank @Email
    private String email;

    @NotBlank
    private String country;

    private String city;

    @Pattern(regexp = "^[0-9]*$")
    private String zipcode;

    public MemberEditDto convertToMemberEditDto(Member member) {
        this.password = member.getPassword();
        this.email = member.getEmail();
        this.country = member.getAddress().getCountry();
        this.city = member.getAddress().getCity();
        this.zipcode = member.getAddress().getZipcode();

        return this;
    }
}
