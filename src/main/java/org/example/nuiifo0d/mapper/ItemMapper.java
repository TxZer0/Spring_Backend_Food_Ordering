package org.example.nuiifo0d.mapper;

import org.example.nuiifo0d.dto.request.ItemRequest;
import org.example.nuiifo0d.dto.request.UpdateItemRequest;
import org.example.nuiifo0d.dto.response.ItemResponse;
import org.example.nuiifo0d.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ItemMapper {
    Item toItem(ItemRequest request);
    ItemResponse toItemResponse(Item item);

    void updateItemFromRequest(UpdateItemRequest request, @MappingTarget Item item);
}
