package com.dtsgroup.userservice.service;

import com.dtsgroup.userservice.dto.UserDTO;
import com.dtsgroup.userservice.dto.UserRequest;
import com.dtsgroup.userservice.dto.UserResponse;
import com.dtsgroup.userservice.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<User> findAll();

    public User save(UserDTO user, MultipartFile image) throws IOException;

    public Optional<User> findById(String id);

    public void delete(String id);

    public User update(String id, UserDTO userDTO, MultipartFile image) throws IOException;

    Optional<User> findByUsername(String username);

    public UserResponse checkLogin(UserRequest userRequest);

    public HashMap<String, Integer> getOrders(String userId);
}
