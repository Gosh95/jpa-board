package jpa.board.domain.dto;

import jpa.board.domain.Address;

import javax.persistence.Embedded;
import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberJoinDto {
    //영어 한글만 2 - 20자
    @NotBlank @Pattern(regexp = "^[a-zA-Z가-힣]{2,20}$")
    private String name;

    //영문 소문 + 숫자 최소 1글자
    @NotBlank @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,}$")
    private String loginId;

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
}
