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
public class CityDto {

    @JsonProperty(value = "city_en_name")
    private String cityEnName;

    @JsonProperty(value = "city_tc_name")
    private String cityTcName;

    @JsonProperty(value = "city_sc_name")
    private String cityScName;

}
