package com.example.springboot.DTO;

import com.example.springboot.jpa.Dusers;
import com.example.springboot.jpa.User;
import com.example.springboot.jpa.UserGroup;
import com.example.springboot.jpa.Yroups;
import lombok.Getter;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GroupResponseDto {


    private Yroups group;

    private int priv;




    public GroupResponseDto(UserGroup entity){
        this.group = entity.getGroup();
        this.priv = entity.getPriv();
    }
}
