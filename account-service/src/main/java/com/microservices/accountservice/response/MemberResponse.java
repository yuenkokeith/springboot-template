package com.microservices.accountservice.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {

    private String memberId;

    private String firstNameEn;

    private String lastNameEn;

    private String firstNameChi;

    private String lastNameChi;

    private Integer titleId;

    private String email;

    private Integer birthdayMonth;

    private Integer birthdayYear;

    private Integer residentialId;

    private Integer districtId;

    private Integer phoneAreaCode;

    private Integer phoneNumber;

    private Boolean picsCheck;

    private Boolean tncCheck;

    private String status;

}
