package org.example.nuiifo0d.service.interfaces;

import org.example.nuiifo0d.dto.request.CartItemRequest;
import org.example.nuiifo0d.dto.request.ItemRequest;
import org.example.nuiifo0d.dto.response.ApiResponse;
import org.example.nuiifo0d.dto.response.CartItemResponse;
import org.example.nuiifo0d.dto.response.ItemResponse;
import org.example.nuiifo0d.entity.CartItem;
import org.example.nuiifo0d.entity.Item;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CartService {
    ApiResponse<List<CartItemResponse>> viewCart();
    ApiResponse<?> addToCart(CartItemRequest request);
    ApiResponse<?> deleteItem(Long itemId);
    ApiResponse<?> updateItemQuantity(Long itemId, String status);
    ApiResponse<?> deleteCart();
}
