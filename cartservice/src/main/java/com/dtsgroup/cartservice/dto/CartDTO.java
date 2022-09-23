package com.dtsgroup.cartservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private String userId;
    private HashMap<String, Integer> orders;
}
