package com.dtsgroup.userservice.service.Impl;

import com.dtsgroup.userservice.config.discoveryclient.CartDiscovery;
import com.dtsgroup.userservice.dto.UserDTO;
import com.dtsgroup.userservice.dto.UserRequest;
import com.dtsgroup.userservice.dto.UserResponse;
import com.dtsgroup.userservice.entity.User;
import com.dtsgroup.userservice.exception.BusinessException;
import com.dtsgroup.userservice.repository.UserRepository;
import com.dtsgroup.userservice.service.UserService;
import com.dtsgroup.userservice.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String CURRENT_URL = "src\\main\\resources\\static\\images";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private CartDiscovery cartDiscovery;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
//        Pageable pageable = (Pageable) PageRequest.of(page, pageSize);
//        Page<User> pageResult = userRepository.findAll(pageable);
//        return pageResult.toList();
    }

    @Override
    public User save(UserDTO userDTO, MultipartFile image) throws IOException {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        String file = new File(CURRENT_URL).getAbsolutePath() + "\\" + image.getOriginalFilename() ;
        OutputStream out = new FileOutputStream(file);
        out.write(image.getBytes());
        out.close();
        user.setImage(image.getOriginalFilename());
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        try {
            Optional<User> optional = findById(id);
            if (!optional.isPresent())
                throw new BusinessException("User does not exist!", HttpStatus.BAD_REQUEST);
            userRepository.delete(optional.get());
        } catch (BusinessException e) {
            LOGGER.error("User does not exist");
        }
    }

    @Override
    public User update(String id, UserDTO userDTO, MultipartFile image){
        User user = null;
        try {
            Optional<User> optional = findById(id);
            if (!optional.isPresent())
                throw new BusinessException("User does not exist!", HttpStatus.BAD_REQUEST);
            user = optional.get();
            user.setUsername(userDTO.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            String file = new File(CURRENT_URL).getAbsolutePath() + "\\" + image.getOriginalFilename() ;
            OutputStream out = new FileOutputStream(file);
            out.write(image.getBytes());
            out.close();
            user.setImage(image.getOriginalFilename());
        } catch (BusinessException e) {
            LOGGER.error("User does not exist");
        } catch (IOException e) {
            LOGGER.error("Error with IO");
        }
        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserResponse checkLogin(UserRequest userRequest) {
        UserResponse userResponse = null;
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userRequest.getUsername(),
                            userRequest.getPassword()
                    )
            );
            User user = (User) authentication.getPrincipal();
            String token = jwtUtil.genarateToken(user.getUsername());
            userResponse = new UserResponse();
            userResponse.setToken(token);
        } catch (BadCredentialsException e ) {
            LOGGER.error("Username or password is invalid");
        }
        return userResponse;
    }

    @Override
    public HashMap<String, Integer> getOrders(String userId) {
        return cartDiscovery.getCartByUserId(userId);
    }


}
