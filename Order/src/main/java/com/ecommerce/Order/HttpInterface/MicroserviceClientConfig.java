package com.ecommerce.Order.HttpInterface;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Optional;

@Configuration
public class MicroserviceClientConfig {

    //Helps collect Metrics and trace Data
    @Autowired
    private ObservationRegistry observationRegistry;

    //Captures span and Trace ID
    @Autowired
    private Tracer tracer;

    @Autowired
    private Propagator propagator;

    @Bean
    @LoadBalanced
    public RestClient.Builder loadBalancedRestClientBuilder() {
        RestClient.Builder builder=  RestClient.builder();

        if(observationRegistry != null) {
            builder.requestInterceptor(createTracingInterceptor());
        }

        return builder;
    }

    private ClientHttpRequestInterceptor createTracingInterceptor() {

        return ((request, body, execution) -> {
            if(tracer != null && propagator != null && tracer.currentSpan() != null) {
                propagator.inject(tracer.currentTraceContext().context(),
                        request.getHeaders(),
                        (carrier, key, value) -> carrier.add(key, value));
            }
            return execution.execute(request, body);
        });

    }

    @Bean
    public ProductServiceClient productServiceClient(RestClient.Builder loadBalancedWebClientBuilder) {
        RestClient restClient = loadBalancedWebClientBuilder.
                baseUrl("http://PRODUCT-MICROSERVICE").
                defaultStatusHandler(HttpStatusCode::is4xxClientError,
                        (request, response) -> Optional.empty()).
                build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(adapter)
                .build();

        return factory.createClient(ProductServiceClient.class);
    }

    @Bean
    public UserServiceClient userServiceClient(RestClient.Builder loadBalancedWebClientBuilder) {
        RestClient restClient = loadBalancedWebClientBuilder.
                baseUrl("http://USER-MICROSERVICE").
                defaultStatusHandler(HttpStatusCode::is4xxClientError,
                        (request, response) -> Optional.empty()).
                build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(adapter)
                .build();

        return factory.createClient(UserServiceClient.class);
    }

}
