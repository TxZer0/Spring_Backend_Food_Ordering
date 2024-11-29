package org.example.nuiifo0d.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.nuiifo0d.dto.request.CartItemRequest;
import org.example.nuiifo0d.dto.request.UpdateQuantity;
import org.example.nuiifo0d.dto.response.ApiResponse;
import org.example.nuiifo0d.dto.response.CartItemResponse;
import org.example.nuiifo0d.service.interfaces.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<CartItemResponse>>> viewCart() {
        return ResponseEntity.ok().body(cartService.viewCart());
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<?>> addToCart(@RequestBody @Valid CartItemRequest request) {
        return ResponseEntity.ok().body(cartService.addToCart(request));
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ApiResponse<?>> updateCart(@PathVariable("itemId") Long itemId, @RequestBody @Valid UpdateQuantity request) {
        return ResponseEntity.ok().body(cartService.updateItemQuantity(itemId, request.getStatus()));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<ApiResponse<?>> removeFromCart(@PathVariable Long itemId) {
        return ResponseEntity.ok().body(cartService.deleteItem(itemId));
    }

    @DeleteMapping("")
    public ResponseEntity<ApiResponse<?>> deleteCart() {
        return ResponseEntity.ok().body(cartService.deleteCart());
    }

}
