package com.crypto.crypto.web;

import com.crypto.crypto.domain.BithumbCoinData;
import com.crypto.crypto.dto.BithumbCoinDataDTO;
import com.crypto.crypto.service.BithumbService;
import com.crypto.crypto.utils.TimeConverter;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BithumbAPI {

  private final BithumbService bithumbService;

  /**
   * 거래소에서 가져온 과거 데이터를 저장하는 메서드
   */
  public void saveCoinHistory(String[] coins) {
    IntStream.range(0, coins.length).forEach(i -> {
      try {
        Gson gson = new Gson();
        String data = getCoinHistory(coins[i]);
        Map<String, Object> map = gson.fromJson(data, Map.class);	// map.get("status"), map.get("data")
        String dataStr = null;

        if (map.get("status").toString().equals("0000")) {				// 통신 성공시 (status code: 0000)
          dataStr = map.get("data").toString();
          dataStr = dataStr.replace("[", "");
          String[] coinData = dataStr.split("], ");

          for (String coinDatum : coinData) {
            String[] dailyData = coinDatum.split(", ");		// {기준 시간, 시가, 종가, 고가, 저가, 거래량}

            BithumbCoinDataDTO bithumbCoinDataDTO =
              bithumbCoinDataDtoFromStringArray(dailyData, i, coins);

            BithumbCoinData bithumbCoinData = bithumbService.addData(bithumbCoinDataDTO);
          }
        }
        Thread.sleep(200);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  /**
   * 거래소에 해당 코인의 과거 데이터를 전부 요청하는 메서드
   */
  public String getCoinHistory(String coin) throws IOException, InterruptedException {
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

    return response.body();
  }

  public String getCoinData(String coin) throws IOException, InterruptedException {
    StringBuilder sb = new StringBuilder();
    sb.append("https://api.bithumb.com/public/ticker/")
      .append(coin);

    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(sb.toString()))
      .header("accept", "application/json")
      .method("GET", HttpRequest.BodyPublishers.noBody())
      .build();
    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    return response.body();
  }

  public String saveCoinData(String coin){
    try {
      String data = getCoinData(coin);
      Gson gson = new Gson();
      Map<String, Object> map = gson.fromJson(data, Map.class);  // map.get("status"), map.get("data")
      String dataStr = null;
      if (map.get("status").toString().equals("0000")) {        // 통신 성공시 (status code: 0000)
        dataStr = map.get("data").toString();
        map = gson.fromJson(dataStr, Map.class);
        LocalDateTime day = TimeConverter.milliToLocalDateTime(String.valueOf(System.currentTimeMillis()));
        BithumbCoinDataDTO bithumbCoinDataDTO = BithumbCoinDataDTO.builder()		// 일별 데이터 DTO 생성
          .coin(coin)
          .candleDateTime(day)
          .highPrice(String.format("%.0f", Double.parseDouble(map.get("max_price").toString())))
          .lowPrice(String.format("%.0f", Double.parseDouble(map.get("min_price").toString())))
          .build();
        BithumbCoinData bithumbCoinData = bithumbService.addData(bithumbCoinDataDTO);
      }
    } catch(Exception e){
      e.printStackTrace();
    }

    return "hello";
  }

  /**
   * 빗썸에서 추출한 일별 코인 데이터를 DTO로 변환하는 메서드.
   * @param dailyData
   * @param coinsIndex
   * @return BithumbCoinDataDTO
   */
  private BithumbCoinDataDTO bithumbCoinDataDtoFromStringArray(String[] dailyData, int coinsIndex,
    String[] coins) {

    LocalDateTime day = TimeConverter.milliToLocalDateTime(dailyData[0]);

    if (dailyData[5].contains("]]")) {
      dailyData[5] = dailyData[5].replace("]]", "");
    }
    BithumbCoinDataDTO bithumbCoinDataDTO = BithumbCoinDataDTO.builder()		// 일별 데이터 DTO 생성
      .coin(coins[coinsIndex])
      .candleDateTime(day)
      .highPrice(dailyData[3])
      .lowPrice(dailyData[4])
      .build();

    return bithumbCoinDataDTO;
  }
}
