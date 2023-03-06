package com.microservices.accountservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberGetRequest {

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

    private String regSrc;

}
