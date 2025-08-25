package com.ecommerce.Order.HttpInterface;

import com.ecommerce.Order.SharedContracts.User.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserServiceClient {

    @GetExchange("/api/users/{id}")
    UserResponse findById(@PathVariable String id);

}
