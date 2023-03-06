package com.microservices.accountservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberPatchResponse {

    private String email;
    private Integer titleId;
    private Integer residentialId;
    private Integer districtId;

}
