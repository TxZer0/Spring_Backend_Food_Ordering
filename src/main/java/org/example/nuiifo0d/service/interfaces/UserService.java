package org.example.nuiifo0d.service.interfaces;

import org.example.nuiifo0d.dto.response.ApiResponse;
import org.example.nuiifo0d.dto.response.ProfileResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UserService {
    ProfileResponse getProfile();
    ApiResponse<?> uploadAvatar(MultipartFile file);
    ResponseEntity<Resource> getAvatar(String fileName);
}
