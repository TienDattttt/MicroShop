package com.microshop.identityservice.service;


import com.microshop.identityservice.dto.request.UserCreationRequest;
import com.microshop.identityservice.dto.request.UserUpdateRequest;
import com.microshop.identityservice.dto.response.UserResponse;
import com.microshop.identityservice.entity.User;
import com.microshop.identityservice.exception.AppException;
import com.microshop.identityservice.exception.ErrorCode;
import com.microshop.identityservice.mapper.UserMapper;
import com.microshop.identityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    public UserResponse create(UserCreationRequest req){
        userRepo.findByUsername(req.getUsername()).ifPresent(u -> { throw new AppException(ErrorCode.USER_EXISTED); });
        User u = User.builder()
                .username(req.getUsername())
                .passwordHash(encoder.encode(req.getPassword()))
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .dob(req.getDob() == null ? null : LocalDate.parse(req.getDob()))
                .enabled(true)
                .build();
        return userMapper.toResponse(userRepo.save(u));
    }

    public UserResponse get(UUID id){
        return userMapper.toResponse(userRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    public List<UserResponse> list(){
        return userRepo.findAll().stream().map(userMapper::toResponse).toList();
    }

    public UserResponse update(UUID id, UserUpdateRequest req){
        User u = userRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (req.getFirstName()!=null) u.setFirstName(req.getFirstName());
        if (req.getLastName()!=null) u.setLastName(req.getLastName());
        if (req.getDob()!=null) u.setDob(LocalDate.parse(req.getDob()));
        if (req.getEnabled()!=null) u.setEnabled(req.getEnabled());
        return userMapper.toResponse(userRepo.save(u));
    }

    public void delete(UUID id){
        if (!userRepo.existsById(id)) throw new AppException(ErrorCode.USER_NOT_FOUND);
        userRepo.deleteById(id);
    }

    public User getByUsernameOrThrow(String username){
        return userRepo.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }
}

