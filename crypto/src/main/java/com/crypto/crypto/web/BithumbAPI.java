package com.crypto.crypto.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BithumbAPI {

  public String getData(String coin) throws IOException, InterruptedException {
    //호출할 코인 URL 빌드
    StringBuilder sb = new StringBuilder();
    sb.append("https://api.bithumb.com/public/candlestick/")
      .append(coin)
      .append("/24h");    //차트 간격: 1m, 3m, 5m, 10m, 30m, 1h, 6h, 12h, 24h 사용 가능

    //업비트 API 요청, JSON 형식으로 데이터 반환
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(sb.toString()))
      .header("accept", "application/json")
      .method("GET", HttpRequest.BodyPublishers.noBody())
      .build();
    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    System.out.println("BithumbAPI " + coin + " 데이터 요청");

    return response.body();
  }
}
