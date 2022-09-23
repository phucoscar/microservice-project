package com.dtsgroup.userservice.controller;

import com.dtsgroup.userservice.dto.UserDTO;
import com.dtsgroup.userservice.entity.User;
import com.dtsgroup.userservice.dto.UserRequest;
import com.dtsgroup.userservice.dto.UserResponse;
import com.dtsgroup.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAll();
    }

//    @GetMapping("/users")
//    public List<User> getAllUsers(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
//                                  @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize) {
//        return userService.findAll(page, pageSize);
//    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.checkLogin(userRequest);
        if (userResponse != null)
            return new ResponseEntity<>(userResponse, HttpStatus.ACCEPTED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE , MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<User> createUser(@RequestPart UserDTO userDTO,
                                           @RequestPart MultipartFile image) throws IOException {
        return new ResponseEntity<>(userService.save(userDTO, image), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> updateUser(@PathVariable String id,
                                             @RequestPart UserDTO userDTO,
                                             @RequestPart MultipartFile image) throws IOException {
        User user = userService.update(id, userDTO, image);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable String id) {
        userService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/cart/{userId}")
    public HashMap<String, Integer> getOrders(@PathVariable String userId) {
        return userService.getOrders(userId);
    }
}
