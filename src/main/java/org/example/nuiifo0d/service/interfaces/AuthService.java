package org.example.nuiifo0d.service.interfaces;

import org.example.nuiifo0d.dto.request.LoginUser;
import org.example.nuiifo0d.dto.request.RegisterUser;
import org.example.nuiifo0d.dto.response.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface AuthService {
    ApiResponse<?> register(RegisterUser request);
    ApiResponse<HashMap<String, String>> login(LoginUser request);

    ApiResponse<?> logout();
}
