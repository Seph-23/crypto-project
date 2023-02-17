package com.crypto.crypto.web;

import com.crypto.crypto.dto.UpbitCoinDataDTO;
import com.crypto.crypto.service.UpbitService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UpbitAPI {
  
  private final UpbitService upbitService;
  
  //Method for basic parsing of json response
  @Transactional
  public String[] getCoinHistory(String coinName, String date) throws Exception {
    if(date.equals(null)){
      HttpResponse<String> response = Unirest.get("https://api.upbit.com/v1/candles/days?market=krw-" + coinName + "&count=200")
              .header("accept", "application/json")
              .asString();
      String[] temp = parseData(response);
      System.out.println("Upbit " + coinName + " 요청");
      return temp;
    }
    else {
      HttpResponse<String> response = Unirest.get("https://api.upbit.com/v1/candles/days?market=krw-" + coinName + "&to=" + date + "%2000%3A00%3A00" + "&count=200")
              .header("accept", "application/json")
              .asString();
      String[] temp = parseData(response);
      System.out.println("Upbit " + coinName + " 요청");
      return temp;
    }
  }

  @Transactional
  public void buildHistory(String[] coins) {
    try {
      String date = Instant.now().toString().split("T")[0];
      for (int i = 0; i < 1; i++) {
        while (true) {
          String[] coinInfo = getCoinHistory(coins[i], date);
          if (coinInfo.length == 1) {
            break;
          }
          saveCoin(coinInfo, coins[i]);
          date = parseDate(coinInfo);
          try {
            Thread.sleep(200);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public String[] parseData(HttpResponse<String> response){
    String testMessage = response.getBody();
    testMessage = testMessage.replace("{", "");
    testMessage = testMessage.replace("[", "");
    String[] coinInfo = testMessage.split("},");
    return coinInfo;
  }
  
  public void saveCoin(String[] coinData, String coinName){
    for (int i = 0; i < coinData.length; i++) {
      String[] individualCoinInfo = coinData[i].split(",");
      String tempInfo = null;
      if (individualCoinInfo[12].contains("}")) {
        tempInfo = (individualCoinInfo[12].replace("}]", "").split("\":")[1]);
      } else tempInfo = (individualCoinInfo[12].split("\":")[1]);
  
      UpbitCoinDataDTO upbitCoinDataDTO = upbitCoinDataDTOBuilder(individualCoinInfo, tempInfo, coinName);
  
      upbitService.addData(upbitCoinDataDTO);
    }
  }
  
  public UpbitCoinDataDTO upbitCoinDataDTOBuilder(String[] individualCoinInfo, String tempInfo, String coinName){
    UpbitCoinDataDTO coin = UpbitCoinDataDTO.builder()
            .coin(coinName)
            .candleDateTime(LocalDateTime.parse(individualCoinInfo[1].split("\":")[1].replace("\"", "")))
            .openingPrice(individualCoinInfo[3].split("\":")[1])
            .highPrice(individualCoinInfo[4].split("\":")[1])
            .lowPrice(individualCoinInfo[5].split("\":")[1])
            .tradePrice(individualCoinInfo[6].split("\":")[1])
            .timestamp(individualCoinInfo[7].split("\":")[1])
            .build();
    return coin;
  }
  
  public String parseDate(String[] coinData){
    String lastSearch = coinData[coinData.length-1];
    String lastCoinDate = lastSearch.split(",")[2];
    String lastKst = lastCoinDate.split("\":\"")[1].replace("\"", "");
    String lastDate = lastKst.split("T")[0];
    return lastDate;
  }
}
