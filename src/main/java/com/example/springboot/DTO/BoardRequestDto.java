package com.example.springboot.DTO;

import com.example.springboot.jpa.Dusers;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDto {

    private String user_name;
    private String user_id;
    private String user_password;



    public Dusers toEntity(){
        return Dusers.builder()
                .userName(user_name)
                .userId(user_id)
                .userPassword(user_password)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}
