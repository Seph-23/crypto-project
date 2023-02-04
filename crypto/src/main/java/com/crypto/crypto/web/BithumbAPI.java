package com.crypto.crypto.web;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.stereotype.Service;

@Service
public class BithumbAPI {

  public void getPrice(String coin) {
    try {
      //호출할 코인 URL 빌드
      StringBuilder sb = new StringBuilder();
      sb.append("https://api.bithumb.com/public/candlestick/")
        .append(coin)
        .append("/24h");

      //업비트 API 요청
      HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(sb.toString()))
        .header("accept", "application/json")
        .method("GET", HttpRequest.BodyPublishers.noBody())
        .build();
      HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      System.out.println(response.body());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
