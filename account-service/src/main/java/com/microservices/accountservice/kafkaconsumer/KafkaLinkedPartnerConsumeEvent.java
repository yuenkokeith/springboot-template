package com.microservices.accountservice.kafkaconsumer;

import com.microservices.accountservice.dto.LinkedPartnerDto;
import com.microservices.accountservice.dto.MemberDto;
import com.microservices.accountservice.request.*;
import com.microservices.accountservice.request.LinkedPartnerGetRequest;
import com.microservices.accountservice.request.LinkedPartnerUpdateRequest;
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
import java.util.Objects;

@Component
public class KafkaLinkedPartnerConsumeEvent {
    Logger LOGGER = LoggerFactory.getLogger(KafkaLinkedPartnerConsumeEvent.class);

    @Autowired
    private MemberService memberService;

    private String recordHeader = "";

    @KafkaListener(groupId = "${gt.consumer-group}", topics = "${gt.send-member-linked-partner-topics}")
    @SendTo
    public Message<?> listenLinkedPartnerTopic(ConsumerRecord<String, Object> consumerRecord) throws IOException {
        LOGGER.info("Received Request From Producer: {}", consumerRecord.value());

        Gson gson = new Gson();

        LinkedPartnerGetRequest linkedPartnerGetRequest = new LinkedPartnerGetRequest();
        LinkedPartnerUpdateRequest linkedPartnerUpdateRequest = new LinkedPartnerUpdateRequest();

        LOGGER.info("==================================================================");

        consumerRecord.headers().headers("kafka_action").forEach(header-> {
            recordHeader = new String(header.value());
            LOGGER.info("crm.member Topics kafka_action, key: {}    value: {}",
                    new String(header.key()) , new String(header.value()));
        });

        MemberDto memberDto = new MemberDto();
        LinkedPartnerDto linkedPartnerDto = new LinkedPartnerDto();

        ErrorCodeResponse errorCodeResponse = new ErrorCodeResponse();
        SuccessCodeResponse successCodeResponse = new SuccessCodeResponse();
        JSONObject jsonObjectResult = new JSONObject();

        // DO {create}, {update}, {get} query and return result
        switch(recordHeader) {
            case "update":
                LOGGER.info("Executing Query for LinkedPartner........ [update]");
                linkedPartnerUpdateRequest = gson.fromJson(consumerRecord.value().toString(), LinkedPartnerUpdateRequest.class);

                if(memberService.updateLinkedPartnerByMemMemberId(linkedPartnerUpdateRequest)) {
                    // return success
                    successCodeResponse.setSuccess(true);
                    jsonObjectResult = successCodeResponse.mapToJsonObject(successCodeResponse);
                    LOGGER.info("Executing Query for LinkedPartner [update] result: Record Updated");
                } else {
                    errorCodeResponse.setErrorCode(400);
                    errorCodeResponse.setErrorMsg("Record Not Update");
                    jsonObjectResult = errorCodeResponse.mapToJsonObject(errorCodeResponse);
                    LOGGER.info("Executing Query for LinkedPartner [update] result: Record Not Update");
                }

                break;

            case "get":
                LOGGER.info("Executing Query for LinkedPartner........ [get]");
                linkedPartnerGetRequest = gson.fromJson(consumerRecord.value().toString(), LinkedPartnerGetRequest.class);
                memberDto = memberService.findByMemMemberId(linkedPartnerGetRequest.getMemberId());

                if(!Objects.isNull(memberDto.getMemMemberId())) {

                    if(memberDto.getMemSmartoneMemberBindDate()!=null) {
                        linkedPartnerDto.setSmartone(true);
                    } else {
                        linkedPartnerDto.setSmartone(false);
                    }

                    if(memberDto.getMemYataMemberBindDate()!=null) {
                        linkedPartnerDto.setYata(true);
                    } else {
                        linkedPartnerDto.setYata(false);
                    }

                    if(memberDto.getMemGoroyalMemberBindDate()!=null) {
                        linkedPartnerDto.setGoRoyal(true);
                    } else {
                        linkedPartnerDto.setGoRoyal(false);
                    }

                    jsonObjectResult = linkedPartnerDto.mapToJsonObject(linkedPartnerDto);
                    LOGGER.info("Executing Query for LinkedPartner [get] result: {}", memberDto.getMemMemberId());
                } else {
                    errorCodeResponse.setErrorCode(400);
                    errorCodeResponse.setErrorMsg("Record Not Found");
                    jsonObjectResult = errorCodeResponse.mapToJsonObject(errorCodeResponse);
                    LOGGER.info("Executing Query for LinkedPartner [get] result: Record Not Found");
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


