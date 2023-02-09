package com.crypto.crypto.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BinanceAPI {

  public String getData(String coin) throws IOException, InterruptedException {
    //호출할 코인 URL 빌드
    StringBuilder sb = new StringBuilder();
    sb.append("https://api.binance.com/api/v3/klines?symbol=")
      .append(coin)
      .append("BUSD&interval=1d")
//      .append("&endTime=1638367800000")
      .append("&limit=1000");

    System.out.println(sb.toString());

    //업비트 API 요청, JSON 형식으로 데이터 반환
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(sb.toString()))
      .header("accept", "application/json")
      .method("GET", HttpRequest.BodyPublishers.noBody())
      .build();
    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    System.out.println(response.body().length());

    return response.body();
  }
}
