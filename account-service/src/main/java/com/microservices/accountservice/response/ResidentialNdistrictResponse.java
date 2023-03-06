package com.microservices.accountservice.response;

import com.microservices.accountservice.dto.CityDto;
import com.microservices.accountservice.dto.DistrictDto;
import com.microservices.accountservice.dto.ProvinceDto;
import com.microservices.accountservice.dto.RegionDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResidentialNdistrictResponse {

    @JsonProperty(value = "residential_id")
    private Integer residentialId;

    @JsonProperty(value = "residential_en_name")
    private String residentialEnName;

    @JsonProperty(value = "residential_tc_name")
    private String residentialTcName;

    @JsonProperty(value = "residential_sc_name")
    private String residentialScName;

    private Set<RegionDto> regions;
    private Set<DistrictDto> districts;
    private Set<ProvinceDto> provinces;
    private Set<CityDto> cites;

}
