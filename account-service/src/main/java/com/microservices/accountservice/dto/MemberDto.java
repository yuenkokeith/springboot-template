package com.microservices.accountservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    Logger LOGGER = LoggerFactory.getLogger(MemberDto.class);

    private Long id;

    private String memFirstname;

    private String memLastName;

    @Size(max=255)
    private String memFirstNameTc;

    @Size(max=255)
    private String memLastNameTc;

    private Integer memSalutation;

    @Email
    private String memEmail;

    @Size(max=18)
    private Integer memMonthOfBirthday;

    @Size(max=18)
    private Integer memYearOfBirthday;

    private Integer memResidential;

    private Integer memDistrict;

    private Integer memCountryCode;

    private Integer memMobilePhone;

    private Boolean memPictureCheck;

    private Boolean memTncAccepted;

    private String accMallPartnerId;

    private String memAccountId;

    @Size(max=255)
    private String memMemberId;

    private Boolean memMemberStatus;

    private String memFavoriteMall1;

    private String memFavoriteMall2;

    private String memFavoriteMall3;

    private LocalDateTime memSmartoneMemberBindDate;

    private LocalDateTime memYataMemberBindDate;

    private LocalDateTime memGoroyalMemberBindDate;

    private Boolean memSmartoneMember;

    private Boolean memYataMember;

    private Boolean memGoroyalMember;

    private Boolean memMarketConsentAccepted;

    public JSONObject mapToJsonObject(MemberDto memberDto) {

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

        jsonObject.put("memberId", memberDto.getMemMemberId()==null ? "" : memberDto.getMemMemberId());
        jsonObject.put("firstNameEn", memberDto.getMemFirstname()==null ? "" : memberDto.getMemFirstname());
        jsonObject.put("lastNameEn", memberDto.getMemLastName()==null ? "" : memberDto.getMemLastName());
        jsonObject.put("firstNameChi", memberDto.getMemFirstNameTc()==null ? "" : memberDto.getMemFirstNameTc() );
        jsonObject.put("lastNameChi", memberDto.getMemLastNameTc()==null ? "" : memberDto.getMemLastNameTc());
        jsonObject.put("titleId", memberDto.getMemSalutation()==null ? "" : memberDto.getMemSalutation());
        jsonObject.put("email", memberDto.getMemEmail()==null ? "" : memberDto.getMemEmail());
        jsonObject.put("birthdayMonth", memberDto.getMemMonthOfBirthday()==null ? "" : memberDto.getMemMonthOfBirthday());
        jsonObject.put("birthdayYear", memberDto.getMemYearOfBirthday()==null ? "" : memberDto.getMemYearOfBirthday());
        jsonObject.put("residentialId", memberDto.getMemResidential()==null ? "" : memberDto.getMemResidential());
        jsonObject.put("districtId", memberDto.getMemDistrict()==null ? "" : memberDto.getMemDistrict());
        jsonObject.put("phoneAreaCode", memberDto.getMemCountryCode()==null ? "" : memberDto.getMemCountryCode());
        jsonObject.put("phoneNumber", memberDto.getMemMobilePhone()==null ? "" : memberDto.getMemMobilePhone());
        jsonObject.put("tncCheck", memberDto.getMemTncAccepted()==null ? "" : memberDto.getMemTncAccepted());
        jsonObject.put("picsCheck", memberDto.getMemPictureCheck()==null ? "" : memberDto.getMemPictureCheck());
        jsonObject.put("status", memberDto.getMemMemberStatus()==null ? "" : memberDto.getMemMemberStatus());

        return jsonObject;
    }
}
