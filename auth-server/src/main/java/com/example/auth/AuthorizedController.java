package com.example.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizedController {

    @GetMapping("/authorized")
    public String authorized(@RequestParam(required = false) String code) {
        if (code == null) {
            return "No authorization code was sent.";
        }
        return "Authorization code: " + code;
    }
}
