package com.microservices.accountservice.controller;

import com.microservices.accountservice.request.MemberGetRequest;
import com.microservices.accountservice.response.*;
import com.microservices.accountservice.response.*;
import com.microservices.accountservice.service.MemberService;
import com.microservices.accountservice.utils.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = {"http://api-gateway:9191", "http://localhost:9191"}, maxAge = 3600)
@RequestMapping("api/v1/member")
public class MemberController {

    Logger LOGGER = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberService memberService;

    @Autowired
    //private MemberRepository memberRepository;

    //private S3Utility s3Utility;

    public MemberController(MemberService memberService) {
        //this.s3Utility = new S3Utility(memberService, memberRepository);
    }


    @PostMapping
    public ResponseEntity<MemberGetRequest> saveMember(@RequestBody MemberGetRequest memberRequest) {
        //String response = memberService.saveMember(memberDto);
        return new ResponseEntity<>(memberRequest, HttpStatus.CREATED);
    }


    // Get All Member with paging
    @GetMapping
    public MemberPageResponse getAllPosts(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                          @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                          @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                          @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return memberService.getAllMembers(pageNo, pageSize, sortBy, sortDir);
    }


    @GetMapping("{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable(name = "id") String id) {

        LOGGER.info("[MemberController] getMember {}", id);

        // Get member by id
        //MemberDto memberDto = memberService.findByMemMemberId(id);
        MemberResponse memberResponse = new MemberResponse();
        memberResponse.setMemberId("AAATEST");
        memberResponse.setFirstNameEn("user 1");
        memberResponse.setLastNameEn("Last Name 1");

        /*
        MemberDto memberDto = memberService.findByMemMemberId(id);
        MemberResponse memberResponse = new MemberResponse();
        memberResponse.setMemberId(memberDto.getMemMemberId());
        memberResponse.setFirstNameEn(memberDto.getMemFirstname());
        memberResponse.setLastNameEn(memberDto.getMemLastName());
        memberResponse.setFirstNameChi(memberDto.getMemFirstNameTc());
        memberResponse.setLastNameChi(memberDto.getMemLastNameTc());
        memberResponse.setTitleId(memberDto.getMemSalutation());
        memberResponse.setEmail(memberDto.getMemEmail());
        memberResponse.setBirthdayMonth(memberDto.getMemMonthOfBirthday());
        memberResponse.setBirthdayYear(memberDto.getMemYearOfBirthday());
        memberResponse.setResidentialId(memberDto.getMemResidential());
        memberResponse.setDistrictId(memberDto.getMemDistrict());
        memberResponse.setPhoneAreaCode(memberDto.getMemCountryCode());
        memberResponse.setPhoneNumber(memberDto.getMemMobilePhone());
        memberResponse.setPicsCheck(memberDto.getMemPictureCheck());
        memberResponse.setStatus(memberDto.getMemMemberStatus());
        */

        return new ResponseEntity<>(memberResponse, HttpStatus.OK);
    }

    @GetMapping("{memberId}/fav-mall")
    public ResponseEntity<FavmallResponse> getFavouriteMall(@PathVariable(name = "memberId") String memberId) {

        // Return array list
        FavmallResponse favmallResponse = new FavmallResponse();
        ArrayList favMallsList = new ArrayList();
        favMallsList.add(10);
        favMallsList.add(20);
        favMallsList.add(30);
        favmallResponse.setFavMalls(favMallsList);

        return new ResponseEntity<>(favmallResponse, HttpStatus.OK);
    }

    @GetMapping("{memberId}/linked-partner")
    public ResponseEntity<LinkedPartnerResponse> getLinkedPartner(@PathVariable(name = "memberId") String memberId) {

        LinkedPartnerResponse linkedPartnerResponse = new LinkedPartnerResponse();
        return new ResponseEntity<>(linkedPartnerResponse, HttpStatus.OK);

    }

    @PatchMapping("{id}")
    public ResponseEntity<MemberPatchResponse> updateUser(@RequestBody MemberPatchResponse memberPatchResponse,
                                                          @PathVariable(name = "id") String id) {

        MemberPatchResponse updatedMemberPatchResponse = new MemberPatchResponse();
        return new ResponseEntity<>(updatedMemberPatchResponse, HttpStatus.OK);

    }

    @PutMapping("{memberId}/fav-mall")
    public ResponseEntity<FavmallResponse> updateFavouriteMall(@RequestBody FavmallResponse favmallResponse,
                                                               @PathVariable(name="memberId") String memberId) {

        return new ResponseEntity<>(favmallResponse, HttpStatus.OK);
    }

    @PutMapping("{memberId}/linked-partner")
    public ResponseEntity<LinkedPartnerResponse> updateLinkedPartner(@RequestBody LinkedPartnerResponse linkedPartnerResponse,
                                                                    @PathVariable(name="memberId") String memberId) {
        return new ResponseEntity<>(linkedPartnerResponse, HttpStatus.OK);
    }



}