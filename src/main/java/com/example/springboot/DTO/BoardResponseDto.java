package com.example.springboot.DTO;

import com.example.springboot.jpa.Dusers;
import com.example.springboot.jpa.User;
import lombok.Getter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {

    private Long userSequenceId;
    private String userId;
    private String userName;


    public BoardResponseDto(Dusers entity){
        this.userId = entity.getUserId();
        this.userName = entity.getUsername();
        this.userSequenceId = entity.getUserSequenceId();

    }
}
