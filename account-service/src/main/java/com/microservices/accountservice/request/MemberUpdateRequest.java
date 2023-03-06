package com.microservices.accountservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequest {

    private String memberId;

    private String email;

    private Integer residentialId;

    private Integer districtId;

}
