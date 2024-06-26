package com.bitcamp.api.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.bitcamp.api.common.component.Messenger;
import com.bitcamp.api.common.component.pagination.PageRequestVo;
import com.bitcamp.api.common.component.security.JwtProvider;
import com.bitcamp.api.user.model.User;
import com.bitcamp.api.user.model.UserDto;
import com.bitcamp.api.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Log4j2
@RequiredArgsConstructor
@Service 
public class UserServiceImpl implements UserService {
    
    private final UserRepository repository;

    private final JwtProvider jwtProvider;
    @Transactional
    @Override
    public Messenger save(UserDto dto) {
        var ent = repository.save(dtoToEntity(dto));
        System.out.println(" ============ UserServiceImpl save instanceof =========== ");
        System.out.println((ent instanceof User) ? "SUCCESS" : "FAILURE");
        return Messenger.builder()
        .message((ent instanceof User) ? "SUCCESS" : "FAILURE")
        .build();
    }

    @Override
    public Messenger deleteById(Long id) {
        repository.deleteById(id);
        return Messenger.builder()
        .message(repository.findById(id).isPresent() ? "SUCCESS" : "FAILURE")
        .build();
    }

    @Override
    public List<UserDto> findAll() { 
        return repository.findAll().stream().map(i->entityToDto(i)).toList();
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        return repository.findById(id).map(i -> entityToDto(i));
    }

    @Override
    public Messenger count() {
        return Messenger.builder()
        .message(repository.count()+"")
        .build();
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public Messenger modify(UserDto dto) {
        var ent = repository.save(dtoToEntity(dto));
        log.info(" ============ BoardServiceImpl modify Entity Debug =========== ");
        log.info(ent);
        System.out.println((ent instanceof User) ? "SUCCESS" : "FAILURE");
        return Messenger.builder()
        .message((ent instanceof User) ? "SUCCESS" : "FAILURE")
        .build();
    }

    @Override
    public List<UserDto> findUsersByName(String name) {
        return repository.findUsersByName(name).stream().map(i->entityToDto(i)).toList();
    }

    @Override
    public List<UserDto> findUsersByJob(String job) {
        return repository.findUsersByJob(job).stream().map(i->entityToDto(i)).toList();
    }


    @Override
    public Optional<User> findUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    // SRP 에 따라 아이디 존재여부를 프론트에서 먼저 판단하고, 넘어옴 (시큐리티)
    @Transactional
    @Override
    public Messenger login(UserDto dto) {
        log.info("로그인 서비스로 들어온 파라미터 : "+dto);
        var user = repository.findByUsername(dto.getUsername()).get();
        var accessToken = jwtProvider.createToken(entityToDto(user));
        var flag = user.getPassword().equals(dto.getPassword());
        // passwordEncoder.matches

        // 토큰을 각 섹션(Header, Payload, Signature)으로 분할
        jwtProvider.printPayload(accessToken);
        
        return Messenger.builder()
        .message(flag ? "SUCCESS" : "FAILURE")
        .accessToken(flag ? accessToken : "None")
        .build();
    }

   

    @Override
    public Boolean existsUsername(String username) {
        var count =repository.existsUsername(username);
        return count  == 1;
    }
    @Transactional
    @Override
    public Boolean logout(String accessToken) {

        Long id = 0L;
        String deltetedToken = "";
        repository.modifyTokenById(id, deltetedToken);
        return repository.findById(id).get().getToken().equals("");

    }

   
    
}
