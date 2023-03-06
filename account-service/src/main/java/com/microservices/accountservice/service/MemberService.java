package com.microservices.accountservice.service;

import com.microservices.accountservice.dto.*;
import com.microservices.accountservice.entity.Member;
import com.microservices.accountservice.repository.MemberRepository;
import com.microservices.accountservice.request.FavmallUpdateRequest;
import com.microservices.accountservice.request.LinkedPartnerUpdateRequest;
import com.microservices.accountservice.request.MemberUpdateRequest;
import com.microservices.accountservice.response.MemberPageResponse;
import com.microservices.accountservice.response.MemberResponse;
import com.microservices.accountservice.dto.MemberDto;

public interface MemberService {

    String saveMember(Member member);
    MemberRepository getMemberRepository();

    MemberPageResponse getAllMembers(int pageNo, int pageSize, String sortBy, String sortDir);

    MemberDto findByMemMemberId(String memberId);

    Boolean updateByMemMemberId(MemberUpdateRequest memberUpdateRequest);

    Boolean updateFavmallByMemMemberId(FavmallUpdateRequest favmallUpdateRequest);

    Boolean updateLinkedPartnerByMemMemberId(LinkedPartnerUpdateRequest linkedPartnerUpdateRequest);

    MemberResponse updateMember(MemberResponse memberResponse, String id);

}
