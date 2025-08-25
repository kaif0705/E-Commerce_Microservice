package com.ecommerce.Order.Service;

import com.ecommerce.Order.HttpInterface.ProductServiceClient;
import com.ecommerce.Order.HttpInterface.UserServiceClient;
import com.ecommerce.Order.Repository.CartItemRepository;
import com.ecommerce.Order.Payload.CartItemRequest;
import com.ecommerce.Order.Model.CartItem;
import com.ecommerce.Order.SharedContracts.Product.ProductResponse;
import com.ecommerce.Order.SharedContracts.User.UserResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    @Autowired
    private final CartItemRepository cartItemRepository;

    @Autowired
    private final ProductServiceClient productServiceClient;

    @Autowired
    private final UserServiceClient userServiceClient;

    public boolean addToCart(String userId, CartItemRequest request) {

        //Fetch product from product service, values are coming from DB
        ProductResponse product= productServiceClient.getProduct(request.getProductId());
        if(product == null) {
            return false;
        }

        //If Product out of stock
        if(request.getStockQuantity() > product.getStockQuantity()) {
            return false;
        }

        //Check if product already exists in the cart

        //Fetch user from user repository
        UserResponse user= userServiceClient.findById(userId);
        if(user == null) {
            return false;
        }
        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, request.getProductId());

        //If product exists, update the quantity of the product.
        if (existingCartItem != null) {
            // Update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getStockQuantity());
            existingCartItem.setPrice(existingCartItem.getPrice().add(product.getPrice()));
            cartItemRepository.save(existingCartItem);

            //Reduce the quantity of the product
//            product.setStockQuantity(product.getStockQuantity() - request.getStockQuantity());


        } else {
            // Create new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getStockQuantity());
            cartItem.setPrice(product.getPrice());
            cartItemRepository.save(cartItem);
        }

        return true;
    }

    public boolean deleteItemFromCart(String userId, String productId) {
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);

        if (cartItem != null){
            cartItemRepository.delete(cartItem);
            return true;
        }
        return false;
    }

    public List<CartItem> getCart(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
