package org.example.nuiifo0d.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.nuiifo0d.dto.request.ItemRequest;
import org.example.nuiifo0d.dto.request.UpdateItemRequest;
import org.example.nuiifo0d.dto.response.ApiResponse;
import org.example.nuiifo0d.dto.response.ItemResponse;
import org.example.nuiifo0d.entity.Item;
import org.example.nuiifo0d.service.interfaces.ItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<?>> getAllItems() {
        return ResponseEntity.ok(itemService.getItems());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<ApiResponse<ItemResponse>> addItem(@RequestBody @Valid ItemRequest request) {
        return ResponseEntity.ok(itemService.addItem(request));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ApiResponse<ItemResponse>> getItemById(@PathVariable("itemId") Long itemId) {
        return ResponseEntity.ok().body(itemService.getItem(itemId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{itemId}")
    public ResponseEntity<ApiResponse<ItemResponse>> updateItem(@PathVariable("itemId") Long itemId, @RequestBody @Valid UpdateItemRequest request) {
        return ResponseEntity.ok().body(itemService.updateItem(itemId, request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{itemId}")
    public ResponseEntity<ApiResponse<?>> deleteItem(@PathVariable("itemId") Long itemId) {
        return ResponseEntity.ok().body(itemService.deleteItem(itemId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("")
    public ResponseEntity<ApiResponse<?>> deleteAllItems() {
        return ResponseEntity.ok().body(itemService.deleteAllItems());
    }

}
