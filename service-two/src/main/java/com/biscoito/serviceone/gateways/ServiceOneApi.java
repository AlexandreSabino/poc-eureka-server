package com.biscoito.serviceone.gateways;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "SERVICE-ONE-APP")
public interface ServiceOneApi {

    @GetMapping(value = "hello")
    String get();
}
