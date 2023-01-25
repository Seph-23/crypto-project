package com.crypto.crypto.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpbitController {
  @GetMapping("/api/upbit/price")
  public String getPrice(){
    try {
      HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://api.upbit.com/v1/candles/minutes/1?market=KRW-BTC&to=2020-02-13%2000%3A00%3A00&count=1"))
        .header("accept", "application/json")
        .method("GET", HttpRequest.BodyPublishers.noBody())
        .build();
      HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      System.out.println(response.body());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "hello";
  }
}