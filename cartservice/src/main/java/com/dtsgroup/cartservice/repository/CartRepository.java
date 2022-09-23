package com.dtsgroup.cartservice.repository;

import com.dtsgroup.cartservice.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {

    public Cart findByUserId(String userId);
}
