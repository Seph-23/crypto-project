package com.crypto.crypto.web;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.stereotype.Service;

@Service
public class UpbitAPI {

  public void getPrice(String coin) {
    try {
      //호출할 코인 URL 빌드
      StringBuilder sb = new StringBuilder();
      sb.append("https://api.upbit.com/v1/candles/days?market=KRW-")
        .append(coin)
        .append("&to=2023-02-04%2023%3A59%3A59&count=200");

      //업비트 API 요청
      HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(sb.toString()))
        .header("accept", "application/json")
        .method("GET", HttpRequest.BodyPublishers.noBody())
        .build();
      HttpResponse<String> response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandlers.ofString());

      String result = response.body().toString();
      System.out.println(result);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
