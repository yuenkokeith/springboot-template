package com.microservices.accountservice.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryCodeResponse implements Serializable {

    @JsonProperty(value = "country-code")
    private Integer countryCode;

}
