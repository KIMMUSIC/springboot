package com.example.springboot.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="yroups")
@Builder
public class Yroups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql의 AUTO_INCREMENT를 그대로 사용
    private Long groupid;

    @Column(nullable = false)
    private String groupname;



}