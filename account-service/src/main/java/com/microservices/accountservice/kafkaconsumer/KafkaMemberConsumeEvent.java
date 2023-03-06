package com.microservices.accountservice.kafkaconsumer;

import com.microservices.accountservice.dto.FavmallDto;
import com.microservices.accountservice.dto.MemberDto;
import com.microservices.accountservice.dto.RegisterMemberMasterCounterDto;
import com.microservices.accountservice.entity.Member;
import com.microservices.accountservice.request.*;
import com.microservices.accountservice.request.*;
import com.microservices.accountservice.response.ErrorCodeResponse;
import com.microservices.accountservice.response.MemberCreateResponse;
import com.microservices.accountservice.response.SuccessCodeResponse;
import com.microservices.accountservice.service.MemberService;
import com.microservices.accountservice.service.RegisterMemberMasterCounterService;
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
public class KafkaMemberConsumeEvent {

    Logger LOGGER = LoggerFactory.getLogger(KafkaMemberConsumeEvent.class);

    @Autowired
    private MemberService memberService;

    @Autowired
    private RegisterMemberMasterCounterService registerMemberMasterCounterService;

    private String recordHeader = "";

    @KafkaListener(groupId = "${gt.consumer-group}", topics = "${gt.send-member-topics}")
    @SendTo
    public Message<?> listen(ConsumerRecord<String, Object> consumerRecord) throws IOException {

        LOGGER.info("Received Request From Producer: {}", consumerRecord.value());

        Gson gson = new Gson();
        MemberGetRequest memberGetRequest = new MemberGetRequest();
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest();
        MemberUpdateRequest memberUpdateRequest = new MemberUpdateRequest();

        /*
        consumerRecord.headers().forEach(header-> {
            LOGGER.info("crm.member Header: {}", new String(header.value()));
        });
         */

        LOGGER.info("==================================================================");

        consumerRecord.headers().headers("kafka_action").forEach(header-> {
            recordHeader = new String(header.value());
            LOGGER.info("crm.member Topics kafka_action, key: {}    value: {}",
                    new String(header.key()) , new String(header.value()));
        });

        MemberDto memberDto = new MemberDto();
        ErrorCodeResponse errorCodeResponse = new ErrorCodeResponse();
        SuccessCodeResponse successCodeResponse = new SuccessCodeResponse();
        MemberCreateResponse memberCreateResponse =  new MemberCreateResponse();
        JSONObject jsonObjectResult = new JSONObject();

        // DO {create}, {update}, {get} query and return result
        switch(recordHeader) {
            case "create":
                LOGGER.info("Executing Query for Member........ [create]");
                memberCreateRequest = gson.fromJson(consumerRecord.value().toString(), MemberCreateRequest.class);

                Integer currentRollingNumber;
                String newMemberId;

                // Find Latest Member Rolling Number
                RegisterMemberMasterCounterDto registerMemberMasterCounterDto = registerMemberMasterCounterService.findTopByOrderByIdDesc();
                if(registerMemberMasterCounterDto!=null) {
                    currentRollingNumber = registerMemberMasterCounterDto.getRollingNumber();

                    // Next Rolling Number
                    currentRollingNumber++;
                    newMemberId = registerMemberMasterCounterDto.getPrefix() + String.format("%07d", currentRollingNumber);

                    LOGGER.info("[create] newMemberId.... {}", newMemberId);

                } else {
                    // insert 1st rolling number record
                    currentRollingNumber = 1;
                    newMemberId = "SHKP" + String.format("%07d", currentRollingNumber);
                    LOGGER.info("[create] 1st newMemberId.... {}", newMemberId);
                }

                Member member = new Member();
                member.setMemMemberId(newMemberId);
                member.setMemFirstname(memberCreateRequest.getFirstNameEn());
                member.setMemLastName(memberCreateRequest.getLastNameEn());
                member.setMemFirstNameTc(memberCreateRequest.getFirstNameChi());
                member.setMemLastNameTc(memberCreateRequest.getLastNameChi());
                member.setMemSalutation(memberCreateRequest.getTitleId());
                member.setMemEmail(memberCreateRequest.getEmail());
                member.setMemMonthOfBirthday(memberCreateRequest.getBirthdayMonth());
                member.setMemYearOfBirthday(memberCreateRequest.getBirthdayYear());
                member.setMemResidential(memberCreateRequest.getResidentialId());
                member.setMemDistrict(memberCreateRequest.getDistrictId());
                member.setMemCountryCode(memberCreateRequest.getPhoneAreaCode());
                member.setMemMobilePhone(memberCreateRequest.getPhoneNumber());
                member.setMemPictureCheck(memberCreateRequest.getPicsCheck());
                member.setMemTncAccepted(memberCreateRequest.getTncCheck());
                member.setMemRegSourceId(memberCreateRequest.getRegSrc());
                member.setMemMarketConsentAccepted(memberCreateRequest.getMarketConsent());
                memberService.saveMember(member);

                MemberDto savedMemberDto = memberService.findByMemMemberId(newMemberId);
                LOGGER.info("[create] save MemberId.... {}", savedMemberDto.getMemMemberId());

                if(savedMemberDto!=null) {
                    // update latest rolling number record after member creation is successful
                    RegisterMemberMasterCounterDto newRegisterMemberMasterCounterDto = new RegisterMemberMasterCounterDto();
                    newRegisterMemberMasterCounterDto.setMemberId(savedMemberDto.getMemMemberId());
                    newRegisterMemberMasterCounterDto.setPrefix("SHKP");
                    newRegisterMemberMasterCounterDto.setRollingNumber(currentRollingNumber);
                    registerMemberMasterCounterService.saveRegisterMemberMasterCounter(newRegisterMemberMasterCounterDto);

                    memberCreateResponse.setMemberId(savedMemberDto.getMemMemberId());
                    jsonObjectResult = memberCreateResponse.mapToJsonObject(memberCreateResponse);
                    LOGGER.info("Executing Query [update] result: Record Updated");

                } else {
                    errorCodeResponse.setErrorCode(400);
                    errorCodeResponse.setErrorMsg("Record Not Created");
                    jsonObjectResult = errorCodeResponse.mapToJsonObject(errorCodeResponse);
                    LOGGER.info("Executing Query [create] result: Record Not Created");
                }
                break;

            case "update":
                LOGGER.info("Executing Query for Member........ [update]");
                memberUpdateRequest = gson.fromJson(consumerRecord.value().toString(), MemberUpdateRequest.class);

                if(memberService.updateByMemMemberId(memberUpdateRequest)) {
                    // return success
                    successCodeResponse.setSuccess(true);
                    jsonObjectResult = successCodeResponse.mapToJsonObject(successCodeResponse);
                    LOGGER.info("Executing Query [update] result: Record Updated");
                } else {
                    errorCodeResponse.setErrorCode(400);
                    errorCodeResponse.setErrorMsg("Record Not Update");
                    jsonObjectResult = errorCodeResponse.mapToJsonObject(errorCodeResponse);
                    LOGGER.info("Executing Query [update] result: Record Not Update");
                }
                break;

            case "get":
                LOGGER.info("Executing Query for Member........ [get]");
                memberGetRequest = gson.fromJson(consumerRecord.value().toString(), MemberGetRequest.class);
                memberDto = memberService.findByMemMemberId(memberGetRequest.getMemberId());

                if(!Objects.isNull(memberDto.getMemMemberId())) {
                    jsonObjectResult = memberDto.mapToJsonObject(memberDto);
                    LOGGER.info("Executing Query [get] result: {}", memberDto.getMemMemberId());
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
                    favmallDto.setFavMalls(favMallsList);
                    jsonObjectResult = favmallDto.mapToJsonObject(favmallDto);
                    LOGGER.info("Executing Query [get] result: {}", memberDto.getMemMemberId());
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