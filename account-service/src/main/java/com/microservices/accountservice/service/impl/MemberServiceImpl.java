package com.microservices.accountservice.service.impl;

import com.microservices.accountservice.dto.MemberDto;
import com.microservices.accountservice.entity.Member;
import com.microservices.accountservice.repository.MemberRepository;
import com.microservices.accountservice.request.FavmallUpdateRequest;
import com.microservices.accountservice.request.LinkedPartnerUpdateRequest;
import com.microservices.accountservice.request.MemberUpdateRequest;
import com.microservices.accountservice.response.MemberPageResponse;
import com.microservices.accountservice.response.MemberResponse;
import com.microservices.accountservice.service.MemberService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);

    private ModelMapper mapper;

    private MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public String saveMember(Member member) {

        Boolean isMemberIdExist = false;
        Boolean isEmailExist = false;

        LOGGER.info("[saveMember] Member object: {} ", member.toString());

        // add check for username exist in database
        if(memberRepository.existsByMemMemberId(member.getMemMemberId())) {
            isMemberIdExist = true;
        }

        // add check for username exist in database
        if(memberRepository.existsByMemMemberId(member.getMemMemberId())) {
            isEmailExist = true;
        }

        if(isMemberIdExist || isEmailExist) {
            LOGGER.info("[saveMember] isMemberIdExist {},  isEmailExist: {}", isMemberIdExist, isEmailExist);
            return "";
        } else {
            LOGGER.info("[saveMember] member email: {}", member.getMemEmail());

            // Assign value to Member Object and then save as a record
            Member newMember = new Member();
            newMember.setMemMemberId(member.getMemMemberId());
            newMember.setMemFirstname(member.getMemFirstname());
            newMember.setMemLastName(member.getMemLastName());
            newMember.setMemFirstNameTc(member.getMemFirstNameTc());
            newMember.setMemLastNameTc(member.getMemLastNameTc());
            newMember.setMemSalutation(member.getMemSalutation());
            newMember.setMemEmail(member.getMemEmail());
            newMember.setMemMonthOfBirthday(member.getMemMonthOfBirthday());
            newMember.setMemYearOfBirthday(member.getMemYearOfBirthday());
            newMember.setMemResidential(member.getMemResidential());
            newMember.setMemDistrict(member.getMemDistrict());
            newMember.setMemCountryCode(member.getMemCountryCode());
            newMember.setMemMobilePhone(member.getMemMobilePhone());
            newMember.setMemPictureCheck(member.getMemPictureCheck());
            newMember.setMemTncAccepted(member.getMemTncAccepted());
            newMember.setAccMallPartnerId(member.getAccMallPartnerId());

            Member savedMember = memberRepository.save(newMember);
            if(savedMember!=null) {
                return savedMember.getMemMemberId();
            } else {
                return "";
            }
        }
    }

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    @Override
    public MemberPageResponse getAllMembers(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort  = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Member> members = memberRepository.findAll(pageable);

        LOGGER.info("Member List: ", members);

        // get content for page object
        List<Member> listOfUsers = members.getContent();

        List<MemberDto> content = listOfUsers.stream().map(member -> mapToDto(member)).collect(Collectors.toList());

        MemberPageResponse memberPageResponse = new MemberPageResponse();

        memberPageResponse.setContent(content);
        memberPageResponse.setPageNo(members.getNumber());
        memberPageResponse.setPageSize(members.getSize());
        memberPageResponse.setTotalElements(members.getTotalElements());
        memberPageResponse.setTotalPages(members.getTotalPages());
        memberPageResponse.setLast(members.isLast());

        return memberPageResponse;
    }

    @Override
    public MemberDto findByMemMemberId(String id) {

        if(memberRepository.existsByMemMemberId(id)) {
            LOGGER.info("[MemberServiceImpl] findByMemMemberId Is Exist ");
            Member member = memberRepository.findByMemMemberId(id);
            MemberDto memberDto = mapToDto(member);
            return memberDto;
        } else {
            LOGGER.info("[MemberServiceImpl] findByMemMemberId Not Exist: ");
            MemberDto emptyDto =  new MemberDto();
            return emptyDto;
        }
    }

    @Override
    public Boolean updateByMemMemberId(MemberUpdateRequest memberUpdateRequest) {

        if(memberRepository.existsByMemMemberId(memberUpdateRequest.getMemberId())) {
            // update record
            Member member = memberRepository.findByMemMemberId(memberUpdateRequest.getMemberId());
            member.setMemEmail(memberUpdateRequest.getEmail());
            member.setMemResidential(memberUpdateRequest.getResidentialId());
            member.setMemDistrict(memberUpdateRequest.getDistrictId());
            Member updatedMember = memberRepository.save(member);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public MemberResponse updateMember(MemberResponse memberResponse, String id) {
        Member member = memberRepository.findByMemMemberId(id);
        member.setMemEmail(memberResponse.getEmail());
        Member updatedMember = memberRepository.save(member);
        MemberDto updatedMemberDto = mapper.map(updatedMember, MemberDto.class);

        MemberResponse updatedMemberResponse = new MemberResponse();
        updatedMemberResponse.setEmail(updatedMemberDto.getMemEmail());
        updatedMemberResponse.setTitleId(updatedMemberDto.getMemSalutation());
        updatedMemberResponse.setResidentialId(updatedMemberDto.getMemResidential());
        updatedMemberResponse.setDistrictId(updatedMemberDto.getMemDistrict());

        return updatedMemberResponse;
    }

    @Override
    public Boolean updateFavmallByMemMemberId(FavmallUpdateRequest favmallUpdateRequest) {

        if(memberRepository.existsByMemMemberId(favmallUpdateRequest.getMemberId())) {
            // update record
            Member member = memberRepository.findByMemMemberId(favmallUpdateRequest.getMemberId());
            member.setMemFavoriteMall1(favmallUpdateRequest.getMemFavoriteMall1());
            member.setMemFavoriteMall2(favmallUpdateRequest.getMemFavoriteMall2());
            member.setMemFavoriteMall3(favmallUpdateRequest.getMemFavoriteMall3());
            Member updatedMember = memberRepository.save(member);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updateLinkedPartnerByMemMemberId(LinkedPartnerUpdateRequest linkedPartnerUpdateRequest) {
        if(memberRepository.existsByMemMemberId(linkedPartnerUpdateRequest.getMemberId())) {
            // update record
            Member member = memberRepository.findByMemMemberId(linkedPartnerUpdateRequest.getMemberId());
            member.setMemSmartoneMember(linkedPartnerUpdateRequest.getLinkedPartner().getSmartone());
            member.setMemYataMember(linkedPartnerUpdateRequest.getLinkedPartner().getYata());
            member.setMemGoroyalMember(linkedPartnerUpdateRequest.getLinkedPartner().getGoRoyal());
            Member updatedMember = memberRepository.save(member);
            return true;
        } else {
            return false;
        }
    }

    // Convert Entity to Dto
    private MemberDto mapToDto(Member member) {

        MemberDto memberDto = mapper.map(member, MemberDto.class);
        return memberDto;
    }

    private Member mapToEntity(MemberDto memberDto) {
        Member member = mapper.map(memberDto, Member.class);
        return member;
    }


}
