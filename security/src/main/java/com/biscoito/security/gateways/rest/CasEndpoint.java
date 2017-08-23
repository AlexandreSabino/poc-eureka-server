package com.biscoito.security.gateways.rest;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(url = "$endpoints.cas.url")
public interface CasEndpoint {

    @GetMapping(value = "")
    Map<String, Object> findUser(final String clientId);
}
