package org.example.nuiifo0d.service.implement;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.nuiifo0d.dto.request.CartItemRequest;
import org.example.nuiifo0d.dto.response.ApiResponse;
import org.example.nuiifo0d.dto.response.CartItemResponse;
import org.example.nuiifo0d.entity.Cart;
import org.example.nuiifo0d.entity.CartItem;
import org.example.nuiifo0d.entity.Item;
import org.example.nuiifo0d.entity.User;
import org.example.nuiifo0d.exception.ResourceNotFoundException;
import org.example.nuiifo0d.repository.CartItemRepository;
import org.example.nuiifo0d.repository.CartRepository;
import org.example.nuiifo0d.repository.ItemRepository;
import org.example.nuiifo0d.repository.UserRepository;
import org.example.nuiifo0d.utils.StatusCodes;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService implements org.example.nuiifo0d.service.interfaces.CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public ApiResponse<List<CartItemResponse>> viewCart() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(auth.getName());

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        var listCartItem = cart.getCartItems().stream()
                .map(CartItemResponse::new) // DTO Mapping
                .collect(Collectors.toList());
        return new ApiResponse<>(StatusCodes.OK, "Success", listCartItem);
    }

    @Override
    public ApiResponse<?> addToCart(CartItemRequest request) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(auth.getName());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));


        if (request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }


        if (item.getQuantity() < request.getQuantity()) {
            throw new IllegalArgumentException("Not enough items in stock");
        }

        //check exist cart
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        //Neu co item do trong gio hang roi
        CartItem cartItem = cartItemRepository.findByCartAndItem(cart, item)
                .orElseGet(() -> {
                    CartItem newCartItem = new CartItem();
                    newCartItem.setCart(cart);
                    newCartItem.setItem(item);
                    newCartItem.setQuantity(request.getQuantity());
                    return newCartItem;
                });

        // Cập nhật số lượng sản phẩm trong giỏ hàng
        cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        cartItemRepository.save(cartItem);

        // Cập nhật lại số lượng item trong kho
        item.setQuantity(item.getQuantity() - request.getQuantity());
        itemRepository.save(item);

        return new ApiResponse<>(StatusCodes.OK, "Success");
    }

    @Override
    @Transactional
    public ApiResponse<?> deleteItem(Long itemId) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(auth.getName());

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        CartItem cartItem = cartItemRepository.findByCartAndItemId(cart, itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found in cart"));

        cartItemRepository.delete(cartItem);

        return new ApiResponse<>(StatusCodes.OK, "Success");
    }

    @Override
    public ApiResponse<?> updateItemQuantity(Long itemId, String status) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(auth.getName());

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        CartItem cartItem = cartItemRepository.findByCartAndItemId(cart, itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found in cart"));

        if(status.equals("inc")){
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }
        else if(status.equals("dec") && cartItem.getQuantity() > 0){
            cartItem.setQuantity(cartItem.getQuantity() - 1);
        }
        else{
            throw new IllegalArgumentException("Something went wrong");
        }

        cartItemRepository.save(cartItem);
        return new ApiResponse<>(StatusCodes.OK, "Success");
    }

    @Override
    @Transactional
    public ApiResponse<?> deleteCart() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(auth.getName());

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        cartItemRepository.deleteAllByCart(cart);

        return new ApiResponse<>(StatusCodes.OK, "Success");
    }

}
