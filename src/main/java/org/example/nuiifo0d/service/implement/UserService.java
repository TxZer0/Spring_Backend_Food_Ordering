package org.example.nuiifo0d.service.implement;


import lombok.RequiredArgsConstructor;
import org.example.nuiifo0d.dto.response.ApiResponse;
import org.example.nuiifo0d.dto.response.ProfileResponse;
import org.example.nuiifo0d.entity.User;
import org.example.nuiifo0d.exception.ResourceNotFoundException;
import org.example.nuiifo0d.repository.UserRepository;
import org.example.nuiifo0d.utils.StatusCodes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.example.nuiifo0d.utils.File.getFileExtension;

@Service
@RequiredArgsConstructor
public class UserService implements org.example.nuiifo0d.service.interfaces.UserService {
    @Value(value = "${file.upload.avatar}")
    private String uploadUrl;
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png");
    private final UserRepository userRepository;

    @Override
    public ProfileResponse getProfile() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findById(Long.parseLong(auth.getName())).orElseThrow(()->
                new ResourceNotFoundException("User not found")
                );
        return new ProfileResponse(user);
    }

    public ApiResponse<?> uploadAvatar(MultipartFile file) {
        if(file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        try {
            Path path = Paths.get(uploadUrl);
            if(!Files.exists(path)) {
                Files.createDirectories(path);
            }
            String extension = getFileExtension(file.getOriginalFilename());
            System.out.println(extension);
            if(!ALLOWED_EXTENSIONS.contains(extension)) {
                throw new IllegalArgumentException("Unsupported file format");
            }

            File des = new File(path.toAbsolutePath() + "/" + UUID.randomUUID() + extension);
            System.out.println( des);
            file.transferTo(des);

            String link = "/api/v1/upload/" + des.getName();

            //save
            var auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userRepository.findById(Long.parseLong(auth.getName())).orElseThrow(()->
                    new ResourceNotFoundException("User not found"));
            user.setAvatarUrl(link);
            userRepository.save(user);

            return new ApiResponse<>(StatusCodes.OK, "Successfully uploaded avatar", "File uploaded: " + link);
        }catch (IOException e){
            throw new IllegalArgumentException("File upload failed: "+e.getMessage());
        }
    }

    public ResponseEntity<Resource>  getAvatar(String fileName) {
        Path filePath = Paths.get(uploadUrl).resolve(fileName);

        if (!filePath.toFile().exists()) {
            return ResponseEntity.notFound().build();
        }
        Resource fileResource = new FileSystemResource(filePath.toFile());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .body(fileResource);
    }

}
