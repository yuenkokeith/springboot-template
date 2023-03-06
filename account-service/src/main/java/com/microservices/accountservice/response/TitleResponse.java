package com.microservices.accountservice.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TitleResponse {

    @JsonProperty(value = "title_id")
    private String titleId;

    @JsonProperty(value = "title_en")
    private String titleEn;

    @JsonProperty(value = "title_tc")
    private String titleTc;

    @JsonProperty(value = "title_sc")
    private String titleSc;

}
