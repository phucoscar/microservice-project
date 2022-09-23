package com.dtsgroup.cartservice.service;

import com.dtsgroup.cartservice.dto.CartDTO;
import com.dtsgroup.cartservice.entity.Cart;

import java.util.List;

public interface CartService {

    public List<Cart> getAll();

    public Cart getByUserId(String user_id);

    public Cart getById(String id);

    public Cart save(CartDTO cartDTO);

}
