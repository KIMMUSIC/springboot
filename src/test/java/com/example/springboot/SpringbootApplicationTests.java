package com.example.springboot;

import com.example.springboot.jpa.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class SpringbootApplicationTests {

    @Autowired
    private UserGroupRepository usergroupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private YroupsRepository yroupsRepository;
    @Test
    void contextLoads() {

        List<UserGroup> us = usergroupRepository.findAll();

        for(UserGroup i : us){
            System.out.println(i.getUser().getAccount());
        }

    }

}
