package com.microservices.accountservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "register_member_master_counter")
public class RegisterMemberMasterCounter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String prefix; // SHKP
    private Integer rollingNumber; // 7 digit 0000001
    private String memberId;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

}
