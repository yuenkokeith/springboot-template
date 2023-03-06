package com.microservices.accountservice.request;
import com.microservices.accountservice.dto.LinkedPartnerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LinkedPartnerUpdateRequest {

    private String memberId;
    private LinkedPartnerDto linkedPartner;
}
