package org.example.nuiifo0d.mapper;

import org.example.nuiifo0d.dto.request.ItemRequest;
import org.example.nuiifo0d.dto.request.RegisterUser;
import org.example.nuiifo0d.entity.Item;
import org.example.nuiifo0d.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RegisterUser request);
}
