package com.crypto.crypto.web;

import com.crypto.crypto.utils.TimeConverter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.crypto.crypto.dto.BinanceCoinDataDTO;
import com.crypto.crypto.service.BinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BinanceAPI {
  
  private final BinanceService binanceService;

  public String getData(String coin, String date) throws IOException, InterruptedException {
    //호출할 코인 URL 빌드
    StringBuilder sb = new StringBuilder();
    sb.append("https://api.binance.com/api/v3/klines?symbol=")
      .append(coin)
      .append("BUSD&interval=1d")
//      .append("&startTime=1676159999999")
      .append("&endTime=")
      .append(date)
      .append("&limit=1000");


    //업비트 API 요청, JSON 형식으로 데이터 반환
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(sb.toString()))
      .header("accept", "application/json")
      .method("GET", HttpRequest.BodyPublishers.noBody())
      .build();
    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    System.out.println("response.statusCode() = " + response.statusCode());

    System.out.println(date);

    System.out.println("Binance " + coin + " 요청");
    return response.body();
  }

  public void buildHistory(String[] coins) {
    try {
      LocalDateTime localDateTime = LocalDateTime.parse("2023-02-19T00:00:00");
      String date = String.valueOf(
        localDateTime.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli());

      for (int i = 0; i < coins.length; i++) {
        while (true) {
          String data = getData(coins[i], date);

          data = data.replace("[[", "[");
          data = data.replace("]]", "]");

          String[] coinData = data.split("],");

          if (date.equals(coinData[0].split(",")[0].replace("[", "")) || data.length() < 3) {
            System.out.println("done");
            break;
          }

          date = coinData[0].split(",")[0].replace("[", "");

          for (int j = 0; j < coinData.length; j++) {
            coinData[j] = coinData[j].replace("[", "");
            coinData[j] = coinData[j].replace("]", "");
            coinData[j] = coinData[j].replace("\"", "");
            String[] dailyData = coinData[j].split(",");
            BinanceCoinDataDTO binanceCoinDataDTO = binanceCoinDataDtoFromStringArray(dailyData,
              coins[i]);

            binanceService.addData(binanceCoinDataDTO);
          }
          Thread.sleep(200);
        }
        Thread.sleep(200);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private BinanceCoinDataDTO binanceCoinDataDtoFromStringArray(String[] dailyData, String coinName) {
    LocalDateTime openTime = TimeConverter.milliToLocalDateTime(dailyData[0]);
    LocalDateTime closeTime = TimeConverter.milliToLocalDateTime(dailyData[6]);
    
    BinanceCoinDataDTO binanceCoinDataDTO = BinanceCoinDataDTO.builder()
            .coin(coinName)
            .candleDateTime(openTime)
            .highPrice(dailyData[2])
            .lowPrice(dailyData[3])
            .build();
    
    return binanceCoinDataDTO;
  }
}
