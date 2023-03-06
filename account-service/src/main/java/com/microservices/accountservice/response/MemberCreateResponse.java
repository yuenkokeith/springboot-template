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
public class MemberCreateResponse {

    Logger LOGGER = LoggerFactory.getLogger(MemberCreateResponse.class);

    private String memberId;

    public JSONObject mapToJsonObject(MemberCreateResponse memberCreateResponse) {

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

        jsonObject.put("memberId", memberCreateResponse.getMemberId()==null ? "" : memberCreateResponse.getMemberId());

        return jsonObject;
    }

}
