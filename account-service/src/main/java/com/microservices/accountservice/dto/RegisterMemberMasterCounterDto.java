package com.microservices.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterMemberMasterCounterDto {

    private String prefix; // SHKP
    private Integer rollingNumber; // 7 digit 0000001
    private String memberId;

}
