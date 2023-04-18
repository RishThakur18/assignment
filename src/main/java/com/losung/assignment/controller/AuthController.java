package com.losung.assignment.controller;

import com.losung.assignment.mapper.UserMapper;
import com.losung.assignment.models.dto.UserDto;
import com.losung.assignment.models.entity.User;
import com.losung.assignment.models.vo.GenericResponseVo;
import com.losung.assignment.repository.UserRepository;
import com.losung.assignment.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("auth")
@Slf4j
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("login")
    public ResponseEntity<GenericResponseVo> getToken(@RequestBody UserDto userDto){
        log.info("incoming req : getToken");

        Optional<User> existingUser = userRepository.findByUserName(userDto.getUserName());

        User user = null;
        String token = null;
        if(existingUser.isPresent()) {
            user = existingUser.get();
            token = jwtService.refreshToken(user);
        }
        else {
            user = userMapper.DtoToEntity(userDto);
            user.setActive(true);
            token = jwtService.generateToken(user);
        }

        user = userRepository.save(user);
        userDto = userMapper.EntityToDto(user);

        Map<String, Object> data = Map.of("user",userDto, "token", token);
        GenericResponseVo genericResponseVo = GenericResponseVo.builder()
                .success(true)
                .message("token generated successfully")
                .data(data)
                .build();
        ResponseEntity<GenericResponseVo> responseEntity = new ResponseEntity<>(genericResponseVo, HttpStatus.OK);

        return responseEntity;
    }
}
