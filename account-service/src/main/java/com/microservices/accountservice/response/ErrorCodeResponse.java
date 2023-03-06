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
public class ErrorCodeResponse extends RuntimeException {

    Logger LOGGER = LoggerFactory.getLogger(ErrorCodeResponse.class);

    private Integer errorCode;
    private String errorMsg;

    public JSONObject mapToJsonObject(ErrorCodeResponse errorCodeResponse) {

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

        jsonObject.put("errorCode", errorCodeResponse.getErrorCode()==null ? "" : errorCodeResponse.getErrorCode());
        jsonObject.put("errorMsg", errorCodeResponse.getErrorMsg()==null ? "" : errorCodeResponse.getErrorMsg());

        return jsonObject;
    }

}
