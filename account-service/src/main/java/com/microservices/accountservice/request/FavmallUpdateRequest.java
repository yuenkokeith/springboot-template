package com.microservices.accountservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavmallUpdateRequest {

    private String memberId;

    private String memFavoriteMall1;

    private String memFavoriteMall2;

    private String memFavoriteMall3;

}
