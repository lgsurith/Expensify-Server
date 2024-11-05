package com.expensify.server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expensify.server.responses.ConversionResponse;
import com.expensify.server.responses.ExchangeResponse;
import com.expensify.server.service.ExchangeService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user/exchange")
public class ExchangeController {
    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    //to test if the route works.
    @GetMapping("/test")
    public String test(){
        return "This Port is functional";
    }

    //to obtain the exchange rates for the given day.
    @GetMapping("/rates")
    public Mono<ExchangeResponse> getRates(){
        return exchangeService.getRates();
    }

    //convert any type of currencies from the given rates.
    @GetMapping("/convert")
    public Mono<ConversionResponse> convert(
        @RequestParam String from,
        @RequestParam String to,
        @RequestParam Double amount){
            return exchangeService.convert(from, to, amount);
        }
    
}
