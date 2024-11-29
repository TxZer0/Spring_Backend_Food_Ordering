package org.example.nuiifo0d.service.implement;

import lombok.RequiredArgsConstructor;
import org.example.nuiifo0d.dto.request.ItemRequest;
import org.example.nuiifo0d.dto.request.UpdateItemRequest;
import org.example.nuiifo0d.dto.response.ApiResponse;
import org.example.nuiifo0d.dto.response.CartItemResponse;
import org.example.nuiifo0d.dto.response.ItemResponse;
import org.example.nuiifo0d.entity.Item;
import org.example.nuiifo0d.exception.ResourceAlreadyExistsException;
import org.example.nuiifo0d.exception.ResourceNotFoundException;
import org.example.nuiifo0d.mapper.ItemMapper;
import org.example.nuiifo0d.repository.ItemRepository;
import org.example.nuiifo0d.utils.StatusCodes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService implements org.example.nuiifo0d.service.interfaces.ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    @Override
    public ApiResponse<?> getItems() {
        List<Item> items = itemRepository.findAll();
        var allItems = items.stream()
                .map(ItemResponse::new)
                .toList();
        return new ApiResponse<>(StatusCodes.OK, "Success", allItems);
    }


    public ApiResponse<ItemResponse> addItem(ItemRequest request) {
        if(itemRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException("Item already exists");
        }
        Item item = itemMapper.toItem(request);
        itemRepository.save(item);
        return new ApiResponse<>(StatusCodes.OK, "Success", itemMapper.toItemResponse(item));
    }

    public ApiResponse<ItemResponse> getItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElse(null);
        if(item == null) {
            throw new ResourceNotFoundException("Item not found");
        }
        return new ApiResponse<>(StatusCodes.OK, "Success", itemMapper.toItemResponse(item));
    }

    public ApiResponse<ItemResponse> updateItem(Long itemId, UpdateItemRequest request) {
        Item item = itemRepository.findById(itemId).orElseThrow(() ->
                new ResourceNotFoundException("Item not found")
        );

        itemMapper.updateItemFromRequest(request, item);
        itemRepository.save(item);
        return new ApiResponse<>(StatusCodes.OK, "Success", itemMapper.toItemResponse(item));
    }

    public ApiResponse<?> deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(()->
                new ResourceNotFoundException("Item not found"
                ));
        itemRepository.delete(item);
        return new ApiResponse<>(StatusCodes.OK, "Success");
    }

    public ApiResponse<?> deleteAllItems(){
        itemRepository.deleteAll();
        return new ApiResponse<>(StatusCodes.OK, "Success");
    }



}
