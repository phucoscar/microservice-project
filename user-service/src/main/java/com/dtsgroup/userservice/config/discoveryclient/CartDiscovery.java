package com.dtsgroup.userservice.config.discoveryclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@Component
public class CartDiscovery {

    @Autowired
    private DiscoveryClient client;

    public HashMap<String, Integer> getCartByUserId(String userId) {
        List<ServiceInstance> siList = client.getInstances("cart-service");
        ServiceInstance si = siList.get(0);
        String url = si.getUri() + "/api/cart/user?userId=" + userId;
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, Integer> orders = restTemplate.getForObject(url, HashMap.class);
        return orders;
    }


}
