package com.dtsgroup.userservice.repository;

import com.dtsgroup.userservice.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    //Page<User> findAll(Pageable pageable);

    Optional<User> findByUsername(String username);
}
