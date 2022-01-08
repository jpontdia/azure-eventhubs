package com.democompany.creditservices.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Configuration
public class RequestHeadersConfig {

    @Value( "${fiserv.subscriptionKey}" )
    private String subscriptionKey;

    @Value("${fiserv.subscriptionValue}")
    private String subscriptionValue;

    @Bean
    public HttpHeaders requestHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(subscriptionKey, subscriptionValue);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
