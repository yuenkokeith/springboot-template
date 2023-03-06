package com.microservices.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LinkedPartnerDto {

    Logger LOGGER = LoggerFactory.getLogger(LinkedPartnerDto.class);

    private Boolean smartone;
    private Boolean yata;
    private Boolean goRoyal;

    public JSONObject mapToJsonObject(LinkedPartnerDto linkedPartnerDto) {

        JSONObject jsonObject = new JSONObject();
        // setup sort order
        try {
            Field changeMap = jsonObject.getClass().getDeclaredField("map");
            changeMap.setAccessible(true);
            changeMap.set(jsonObject, new LinkedHashMap<>());
            changeMap.setAccessible(false);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            LOGGER.info(e.getMessage());
        }

        jsonObject.put("smartone", linkedPartnerDto.getSmartone()==null ? "" : linkedPartnerDto.getSmartone());
        jsonObject.put("yata", linkedPartnerDto.getYata()==null ? "" : linkedPartnerDto.getYata());
        jsonObject.put("goRoyal", linkedPartnerDto.getGoRoyal()==null ? "" : linkedPartnerDto.getGoRoyal());

        return jsonObject;
    }
}
