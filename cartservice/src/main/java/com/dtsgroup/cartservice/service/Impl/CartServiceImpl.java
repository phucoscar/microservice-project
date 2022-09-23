package com.dtsgroup.cartservice.service.Impl;

import com.dtsgroup.cartservice.dto.CartDTO;
import com.dtsgroup.cartservice.entity.Cart;
import com.dtsgroup.cartservice.repository.CartRepository;
import com.dtsgroup.cartservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Cart> getAll() {
        return cartRepository.findAll();
    }

    @Override
    public Cart getByUserId(String userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Cart getById(String id) {
        Optional<Cart> op = cartRepository.findById(id);
        Cart cart = null;
        if (op.isPresent()) {
            cart = op.get();
        }
        return cart;
    }

    @Override
    public Cart save(CartDTO cartDTO) {
        Cart cart = new Cart();
        cart.setUserId(cartDTO.getUserId());
        cart.setOrders(cartDTO.getOrders());
        return cartRepository.save(cart);
    }


}
