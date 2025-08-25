package com.ecommerce.Order.HttpInterface;

import com.ecommerce.Order.SharedContracts.Product.ProductResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ProductServiceClient {

    @GetExchange("/api/products/{id}")
    ProductResponse getProduct(@PathVariable String id);

}
