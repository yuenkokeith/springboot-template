package com.microservices.accountservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name ="member")
@NoArgsConstructor
@AllArgsConstructor
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String memFirstname;

    @Column(nullable = false)
    private String memLastName;

    @Column(length = 255)
    private String memFirstNameTc;

    @Column(length = 255)
    private String memLastNameTc;

    private Integer memSalutation;

    @Email
    private String memEmail;

    @Column(length = 18)
    private Integer memMonthOfBirthday;

    @Column(length = 18)
    private Integer memYearOfBirthday;

    private Integer memResidential;

    private Integer memDistrict;

    private Integer memCountryCode;

    private Integer memMobilePhone;

    private Boolean memPictureCheck;

    private Boolean memTncAccepted;

    private String accMallPartnerId;

    @Column(unique = true)
    private String memAccountId;

    @Column(length = 255, unique = true)
    private String memMemberId;

    private Boolean memMemberStatus;

    @Column(name = "mem_favorite_mall_1")
    private String memFavoriteMall1;

    @Column(name = "mem_favorite_mall_2")
    private String memFavoriteMall2;

    @Column(name = "mem_favorite_mall_3")
    private String memFavoriteMall3;

    private String memRegSourceId;

    @Column(name = "mem_market_consent")
    private Boolean memMarketConsentAccepted;

    private LocalDateTime memSmartoneMemberBindDate;

    private LocalDateTime memYataMemberBindDate;

    private LocalDateTime memGoroyalMemberBindDate;

    private Boolean memSmartoneMember;

    private Boolean memYataMember;

    private Boolean memGoroyalMember;

    private LocalDateTime updatedAt;

    private LocalDateTime createdAt;

}
