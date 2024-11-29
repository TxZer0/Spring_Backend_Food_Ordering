package org.example.nuiifo0d.service.interfaces;

import org.example.nuiifo0d.dto.request.ItemRequest;
import org.example.nuiifo0d.dto.request.UpdateItemRequest;
import org.example.nuiifo0d.dto.response.ApiResponse;
import org.example.nuiifo0d.dto.response.ItemResponse;
import org.example.nuiifo0d.entity.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {
    ApiResponse<?> getItems();
    ApiResponse<ItemResponse> addItem(ItemRequest request);
    ApiResponse<ItemResponse> getItem(Long itemId);
    ApiResponse<ItemResponse> updateItem(Long itemId, UpdateItemRequest request);
    ApiResponse<?> deleteItem(Long itemId);
    ApiResponse<?> deleteAllItems();
}
