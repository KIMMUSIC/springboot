package com.example.springboot.Service;

import com.example.springboot.DTO.BoardRequestDto;
import com.example.springboot.DTO.BoardResponseDto;
import com.example.springboot.jpa.Dusers;
import com.example.springboot.jpa.DusersRepository;
import com.example.springboot.jpa.User;
import com.example.springboot.jpa.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final DusersRepository dusersRepository;

    private final PasswordEncoder passwordEncoder;


    @Transactional
    public Long save(final BoardRequestDto params){
        String encodedPassword = passwordEncoder.encode(params.getUser_password());
        params.setUser_password(encodedPassword);
        Dusers entity = dusersRepository.save(params.toEntity());
        return entity.getUserSequenceId();
    }



    @Transactional
    public List<BoardResponseDto> getBoardList(){
        List<Dusers> dusers = dusersRepository.findAll();
        return dusers.stream().map(BoardResponseDto::new).collect(Collectors.toList());

    }
}
