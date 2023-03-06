package com.microservices.accountservice.response;

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
public class SuccessCodeResponse {

    Logger LOGGER = LoggerFactory.getLogger(SuccessCodeResponse.class);

    private Boolean success;

    public JSONObject mapToJsonObject(SuccessCodeResponse successCodeResponse) {

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

        jsonObject.put("success", successCodeResponse.getSuccess()==null ? "" : successCodeResponse.getSuccess());

        return jsonObject;
    }

}
