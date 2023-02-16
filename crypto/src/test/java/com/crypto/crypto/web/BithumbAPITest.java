package com.crypto.crypto.web;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public class BithumbAPI {

  public List<Candle> initBithumbCoinData(String coin) throws IOException, InterruptedException {
    String rawData = getData(coin);

    List<Candle> candles = new ArrayList<>();
    ObjectMapper objectMapper = new ObjectMapper();

    // JSON 문자열 파싱 후 Candle 리스트 반환
    JsonNode rootNode = objectMapper.readTree(rawData);
    JsonNode dataNode = rootNode.get("data");

    for (JsonNode candleNode : dataNode) {
      Candle candle = new Candle();
      candle.setCoin(coin);
      candle.setCandleDateTimeUtc(candleNode.get("candle_date_time_utc").asText());
      candle.setOpeningPrice(candleNode.get("opening_price").asDouble());
      candle.setHighPrice(candleNode.get("high_price").asDouble());
      candle.setLowPrice(candleNode.get("low_price").asDouble());
      candle.setTradePrice(candleNode.get("trade_price").asDouble());
      candle.setCandleAccTradeVolume(candleNode.get("candle_acc_trade_volume").asDouble());
      candle.setCandleAccTradePrice(candleNode.get("candle_acc_trade_price").asDouble());

      candles.add(candle);
    }

    return candles;
  }

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