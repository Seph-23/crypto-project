package com.crypto.crypto.web;

import com.crypto.crypto.dto.UpbitCoinDataDTO;
import com.crypto.crypto.service.UpbitService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpbitAPI {
  
  private final UpbitService upbitService;
  
  //Send and receives records of cryptocurrency history
  public String[] callCoinHistory(String coinName, String date) throws Exception {
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
  
  public String[] getCoinData(String coin) throws Exception{
    HttpResponse<String> response = Unirest.get("https://api.upbit.com/v1/candles/minutes/1?market=krw-" + coin + "&count=1" )
            .header("accept", "application/json")
            .asString();
    String[] temp = parseData(response);
    System.out.println("Upbit " + coin + " 요청");
    return temp;
  }

  public void saveCoinHistory(String[] coins) {
    try {
      String date = Instant.now().toString().split("T")[0];
      for (int i = 0; i < coins.length; i++) {
        while (true) {
          String[] coinInfo = callCoinHistory(coins[i], date);
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
        Thread.sleep(200);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void saveCoinData(String coin) throws Exception{
    String[] coinInfo = getCoinData(coin);
    saveCoin(coinInfo, coin);
    System.out.println("Upbit" + coin + "updated.");
  }
  
  private String[] parseData(HttpResponse<String> response){
    String testMessage = response.getBody();
    testMessage = testMessage.replace("{", "");
    testMessage = testMessage.replace("[", "");
    String[] coinInfo = testMessage.split("},");
    return coinInfo;
  }
  
  private void saveCoin(String[] coinData, String coinName){
    for (int i = 0; i < coinData.length; i++) {
      String[] individualCoinInfo = coinData[i].split(",");
      String tempString = null;
      if (individualCoinInfo[12].contains("}")) {
        tempString = (individualCoinInfo[12].replace("}]", "").split("\":")[1]);
      } else tempString = (individualCoinInfo[12].split("\":")[1]);
  
      UpbitCoinDataDTO upbitCoinDataDTO = upbitCoinDataDTOBuilder(individualCoinInfo, tempString, coinName);
  
      upbitService.addData(upbitCoinDataDTO);
    }
  }
  
  private UpbitCoinDataDTO upbitCoinDataDTOBuilder(String[] individualCoinInfo, String tempInfo, String coinName){
    UpbitCoinDataDTO coin = UpbitCoinDataDTO.builder()
            .coin(coinName)
            .candleDateTime(LocalDateTime.parse(individualCoinInfo[1].split("\":")[1].replace("\"", "")))
            .highPrice(individualCoinInfo[4].split("\":")[1])
            .lowPrice(individualCoinInfo[5].split("\":")[1])
            .build();
    return coin;
  }
  
  private String parseDate(String[] coinData){
    String lastSearch = coinData[coinData.length-1];
    String lastCoinDate = lastSearch.split(",")[2];
    String lastKst = lastCoinDate.split("\":\"")[1].replace("\"", "");
    String lastDate = lastKst.split("T")[0];
    return lastDate;
  }
}
