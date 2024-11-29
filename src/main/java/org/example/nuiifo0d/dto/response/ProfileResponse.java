package org.example.nuiifo0d.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.nuiifo0d.entity.User;
import org.example.nuiifo0d.utils.HideData;

@Getter
@Setter
public class ProfileResponse {
    private String username;
    private String email;
    private String phone;
    private String location;
    private String avatarUrl;

    public ProfileResponse(User user) {
        username = user.getUsername();
        email = HideData.hideEmail(user.getEmail());
        phone = HideData.hidePhone(user.getPhone());
        location = user.getLocation();
    }

}
