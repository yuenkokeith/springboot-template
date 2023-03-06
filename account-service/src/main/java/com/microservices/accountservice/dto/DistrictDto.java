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
public class DistrictDto {

    @JsonProperty(value = "district_id")
    private Integer districtId;

    @JsonProperty(value = "district_en_name")
    private String districtEnName;

    @JsonProperty(value = "district_tc_name")
    private String districtTcName;

    @JsonProperty(value = "district_sc_name")
    private String districtScName;

}
