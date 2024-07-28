package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RestController
public class HelloWorldController {

    private MyassetApiClient myassetApiClient;

    public HelloWorldController(MyassetApiClient myassetApiClient) {
        this.myassetApiClient = myassetApiClient;
    }

    @GetMapping("/helloworld")
    public String helloWorld(
            @RequestParam(defaultValue = "10") Integer t
    ) {

        String res = myassetApiClient.getCardSummary("testIdNo", YearMonth.of(2023, 1));

        return "Hello World! : " + res;
    }
}
