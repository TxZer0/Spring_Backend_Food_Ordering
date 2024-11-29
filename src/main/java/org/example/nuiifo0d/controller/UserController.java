package org.example.nuiifo0d.controller;


import lombok.RequiredArgsConstructor;
import org.example.nuiifo0d.dto.response.ApiResponse;
import org.example.nuiifo0d.entity.User;
import org.example.nuiifo0d.service.implement.UserService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        return ResponseEntity.ok().body(userService.getProfile());
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<?>> upload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(userService.uploadAvatar(file));
    }

    @GetMapping("/upload/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable("fileName") String fileName){
        return userService.getAvatar(fileName);
    }
}
