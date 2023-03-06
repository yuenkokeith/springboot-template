package com.microservices.accountservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceDto {

    @JsonProperty(value = "province_en_name")
    private String provinceEnName;

    @JsonProperty(value = "province_tc_name")
    private String provinceTcName;

    @JsonProperty(value = "province_sc_name")
    private String provinceScName;

}
