package com.example.DifferentWorlds.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;


@Getter
@Setter
public class ResponseDTO {
    private String token;
    private long expiresIn;
    private String username;
    private String phoneNum;
    private String message;
    private Long id;

    @Autowired
    // TODO why using Autowired on dto class
    // TODO model class should not depend on any external library
    public ResponseDTO(String token, long expiresIn,String phoneNum ,String username, long id, String message){
        this.token=token;
        this.expiresIn=expiresIn;
        this.username=username;
        this.phoneNum=phoneNum;
        this.id= id;
        this.message = message;
    }
    @Autowired
    public ResponseDTO(String token,String phoneNum ,String username, long id){
        this.token=token;
        this.username=username;
        this.phoneNum=phoneNum;
        this.id= id;
    }
    @Autowired
    public ResponseDTO(String token,String username, long id){
        this.token=token;
        this.username=username;
        this.id= id;
    }
    @Autowired
    public ResponseDTO(String token){
        this.token=token;
    }

    @Autowired
    public ResponseDTO(){}


//    public LoginResponse setToken(String token) {
//        this.token = token;
//        return this;
//    }
//
//    public LoginResponse setExpiresIn(long expiresIn) {
//        this.expiresIn = expiresIn;
//        return this;
//    }
}

