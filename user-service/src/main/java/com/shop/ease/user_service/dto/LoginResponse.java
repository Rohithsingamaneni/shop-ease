package com.shop.ease.user_service.dto;

import lombok.Getter;

@Getter
public class LoginResponse {
    private String token;

    public LoginResponse(String token){
        this.token=token;
    }
}
