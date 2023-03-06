package com.microservices.accountservice.kafkaconsumer;

import com.microservices.accountservice.dto.FavmallDto;
import com.microservices.accountservice.dto.MemberDto;
import com.microservices.accountservice.request.*;
import com.microservices.accountservice.request.FavmallGetRequest;
import com.microservices.accountservice.request.FavmallUpdateRequest;
import com.microservices.accountservice.response.ErrorCodeResponse;
import com.microservices.accountservice.response.SuccessCodeResponse;
import com.microservices.accountservice.service.MemberService;
import com.google.gson.Gson;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@Component
public class KafkaFavmallConsumeEvent {

    Logger LOGGER = LoggerFactory.getLogger(KafkaFavmallConsumeEvent.class);

    @Autowired
    private MemberService memberService;

    private String recordHeader = "";

    @KafkaListener(groupId = "${gt.consumer-group}", topics = "${gt.send-member-favmall-topics}")
    @SendTo
    public Message<?> listenFavmallTopic(ConsumerRecord<String, Object> consumerRecord) throws IOException {

        LOGGER.info("Received Request From Producer: {}", consumerRecord.value());

        Gson gson = new Gson();

        FavmallGetRequest favmallGetRequest = new FavmallGetRequest();
        FavmallUpdateRequest favmallUpdateRequest = new FavmallUpdateRequest();

        LOGGER.info("==================================================================");

        consumerRecord.headers().headers("kafka_action").forEach(header-> {
            recordHeader = new String(header.value());
            LOGGER.info("crm.member Topics kafka_action, key: {}    value: {}",
                    new String(header.key()) , new String(header.value()));
        });

        MemberDto memberDto = new MemberDto();
        FavmallDto favmallDto = new FavmallDto();

        ErrorCodeResponse errorCodeResponse = new ErrorCodeResponse();
        SuccessCodeResponse successCodeResponse = new SuccessCodeResponse();
        JSONObject jsonObjectResult = new JSONObject();

        // DO {create}, {update}, {get} query and return result
        switch(recordHeader) {
            case "update":
                LOGGER.info("Executing Query for Favmall........ [update]");
                favmallUpdateRequest = gson.fromJson(consumerRecord.value().toString(), FavmallUpdateRequest.class);

                /*
                Integer retryCount = 0;
                Boolean isSuccess = true;
                while( !memberService.updateFavmallByMemMemberId(favmallUpdateRequest) ) {

                    if(retryCount>2) {
                        errorCodeResponse.setErrorCode(400);
                        errorCodeResponse.setErrorMsg("Record Not Update");
                        jsonObjectResult = errorCodeResponse.mapToJsonObject(errorCodeResponse);
                        LOGGER.info("Executing Query for Favmall [update] result: Record Not Update and Retried {} times.", retryCount);
                        break;
                    }
                    retryCount++;
                }
                */

                if(memberService.updateFavmallByMemMemberId(favmallUpdateRequest)) {
                    // return success
                    successCodeResponse.setSuccess(true);
                    jsonObjectResult = successCodeResponse.mapToJsonObject(successCodeResponse);
                    LOGGER.info("Executing Query for Favmall [update] result: Record Updated");
                } else {
                    errorCodeResponse.setErrorCode(400);
                    errorCodeResponse.setErrorMsg("Record Not Update");
                    jsonObjectResult = errorCodeResponse.mapToJsonObject(errorCodeResponse);
                    LOGGER.info("Executing Query for Favmall [update] result: Record Not Update");
                }

                break;

            case "get":
                LOGGER.info("Executing Query for Favmall........ [get]");
                favmallGetRequest = gson.fromJson(consumerRecord.value().toString(), FavmallGetRequest.class);
                memberDto = memberService.findByMemMemberId(favmallGetRequest.getMemberId());

                if(!Objects.isNull(memberDto.getMemMemberId())) {
                    ArrayList favMallsList = new ArrayList();
                    favMallsList.add(memberDto.getMemFavoriteMall1());
                    favMallsList.add(memberDto.getMemFavoriteMall2());
                    favMallsList.add(memberDto.getMemFavoriteMall3());
                    LOGGER.info("Executing Query [get] result: {}", memberDto.getMemMemberId());

                    // Return array directly
                    return MessageBuilder.withPayload(favMallsList.toString())
                            .setHeader("kafka_action", recordHeader+ "-reply")
                            .build();
                } else {
                    errorCodeResponse.setErrorCode(400);
                    errorCodeResponse.setErrorMsg("Record Not Found");
                    jsonObjectResult = errorCodeResponse.mapToJsonObject(errorCodeResponse);
                    LOGGER.info("Executing Query [get] result: Record Not Found");
                }

                break;

            default:
                // code block
        }

        // kafka_action header: create, create-reply, get, get-reply, update, update-reply,
        return MessageBuilder.withPayload(jsonObjectResult.toString())
                .setHeader("kafka_action", recordHeader+ "-reply")
                .build();
    }

}