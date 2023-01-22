package com.crypto.crypto.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class HomeController {

  @GetMapping("/api/user")
  public String getUser() {
    return "hello";
  }

  @PostMapping("/api/user2")
  public String postUser() {
    return "hello2";
  }
}