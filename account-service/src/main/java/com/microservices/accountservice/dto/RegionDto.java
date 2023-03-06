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
public class RegionDto {

    @JsonProperty(value = "region_en_name")
    private String regionEnName;

    @JsonProperty(value = "region_tc_name")
    private String regionTcName;

    @JsonProperty(value = "region_sc_name")
    private String regionScName;
}
