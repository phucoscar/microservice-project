package com.dtsgroup.cartservice.controller;

import com.dtsgroup.cartservice.dto.CartDTO;
import com.dtsgroup.cartservice.entity.Cart;
import com.dtsgroup.cartservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public List<Cart> getAllOrder() {
        return cartService.getAll();
    }

    @GetMapping("/user")
    public HashMap<String, Integer> getCartOrdersByUserId(@RequestParam("userId") String userId) {
        Cart cart = cartService.getByUserId(userId);
        return cart.getOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCartById(@PathVariable String id) {
        Cart cart = cartService.getById(id);
        if (cart != null) {
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Cart>  createCart(@RequestBody CartDTO cartDTO) {
        return new ResponseEntity<>(cartService.save(cartDTO), HttpStatus.CREATED);
    }


}
