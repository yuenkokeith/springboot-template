package com.microservices.accountservice.repository;

import com.microservices.accountservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

        Optional<Member> findByMemAccountId(String memAccountId);

        Member findByMemMemberId(String memMemberId);

        // check if MemAccountId exists
        Boolean existsByMemAccountId (String memAccountId);

        Boolean existsByMemMemberId (String memMemberId);
        Boolean existsByMemEmail (String memEmail);
}
