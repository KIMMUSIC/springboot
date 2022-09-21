package com.example.springboot.DTO;

import com.example.springboot.jpa.Dusers;
import com.example.springboot.jpa.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
public class LoginResponseDto {

    private String accessToken;

    private String userName;

}
