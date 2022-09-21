package com.example.springboot.Controller;
import com.example.springboot.DTO.BoardRequestDto;
import com.example.springboot.DTO.BoardResponseDto;
import com.example.springboot.DTO.GroupResponseDto;
import com.example.springboot.Service.BoardService;
import com.example.springboot.config.JwtTokenProvider;
import com.example.springboot.jpa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class TestController {

    private final BoardService boardService;
    private final JwtTokenProvider jwtTokenProvider;

    private final DusersRepository dusersRepository;

    private final UserGroupRepository userGroupRepository;

    private final YroupsRepository yroupsRepository;
    @GetMapping("/api/hello")
    public String hello(){
        return "board/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "board/signup";
    }


    @PostMapping("/signup")
    public @ResponseBody Long save(@RequestBody final BoardRequestDto params){
        return boardService.save(params);

    }


    @GetMapping("/board")
    public String list(Model model){
        List<BoardResponseDto> list = boardService.getBoardList();
        model.addAttribute("boardList", list);
        return "board/list";
    }

    @GetMapping("/grouplist")
    public @ResponseBody List<GroupResponseDto> group(HttpServletResponse response, HttpServletRequest request){
        String userpk = "KIMMUSIC";
        Dusers user = dusersRepository.findByUserId(userpk);
        List<UserGroup> userGroup = userGroupRepository.findByuser(user);
        return userGroup.stream().map(GroupResponseDto::new).collect(Collectors.toList());

    }
}
