package org.example.nuiifo0d.service.implement;

import lombok.RequiredArgsConstructor;
import org.example.nuiifo0d.dto.request.LoginUser;
import org.example.nuiifo0d.dto.request.RegisterUser;
import org.example.nuiifo0d.dto.response.ApiResponse;
import org.example.nuiifo0d.entity.RefreshTokensUsed;
import org.example.nuiifo0d.entity.User;
import org.example.nuiifo0d.enums.Role;
import org.example.nuiifo0d.exception.InvalidCredentialsException;
import org.example.nuiifo0d.exception.ResourceAlreadyExistsException;
import org.example.nuiifo0d.exception.ResourceNotFoundException;
import org.example.nuiifo0d.mapper.UserMapper;
import org.example.nuiifo0d.repository.RTokensUsedRepository;
import org.example.nuiifo0d.repository.UserRepository;
import org.example.nuiifo0d.utils.StatusCodes;
import org.example.nuiifo0d.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService implements org.example.nuiifo0d.service.interfaces.AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RTokensUsedRepository rTokensUsedRepository;
    private final UserMapper userMapper;
    @Autowired
    private final Token token;

    @Override
    public ApiResponse<?> register(RegisterUser request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already in use");
        }

        if(userRepository.existsByUsername(request.getUsername())) {
            throw new ResourceAlreadyExistsException("Username already in use");
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = userMapper.toUser(request);
        if(user == null) {
            throw new RuntimeException("Something went wrong");
        }
        user.setRole(Role.USER);
        userRepository.save(user);
        return new ApiResponse<>(StatusCodes.CREATED, "Registered Successfully");

    }

    @Override
    public ApiResponse<HashMap<String, String>> login(LoginUser request) {
        User user = userRepository.findByEmail(request.getEmail());
        if(user == null) {
            throw new ResourceNotFoundException("Not found");
        }
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Unauthorized");
        }

        var tokens = token.createTokenPair(user);
        user.setRefreshToken(tokens.get("refresh_token"));
        userRepository.save(user);
        return new ApiResponse<>(StatusCodes.CREATED, "Login Successfully", tokens);
    }

    @Override
    @Transactional
    public ApiResponse<?> logout() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("Not found");
        }
        user.setRefreshToken(null);
        userRepository.save(user);
        rTokensUsedRepository.deleteByUserId(user.getId());
        SecurityContextHolder.clearContext();
        return new ApiResponse<>(StatusCodes.OK, "Logout Successfully");
    }




}
