package com.biscoito.serviceone.controllers;

import com.biscoito.serviceone.gateways.ServiceOneApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "feign")
public class FeignRestController {

    @Autowired
    private ServiceOneApi serviceOneApi;

    @GetMapping(value = "test")
    public String get() {
        return String.format("Resposta do serviço one: %s", serviceOneApi.get());
    }
}
