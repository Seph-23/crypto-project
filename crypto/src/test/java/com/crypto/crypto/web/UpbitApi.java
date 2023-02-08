package com.crypto.crypto.web;

import com.crypto.crypto.dto.UpbitCoinDataDTO;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;

public class UpbitApi {
  static final String coinName = "btc";
  //, "eth", "xrp", "ada", "doge", "matic", "sol", "dot", "avax"
  static final HttpResponse<String> string = null;
  
  @Test
  public void returnStatus() throws Exception {
    HttpResponse<String> response = Unirest.get("https://api.upbit.com/v1/candles/days?market=krw-" + coinName + "&count=200")
            .header("accept", "application/json")
            .asString();
    //System.out.println(response.getStatus());
    //System.out.println(response.getBody());
    
    String testMessage = response.getBody();
    //System.out.println(testMessage);
    testMessage = testMessage.replace("{", "");
    testMessage = testMessage.replace("[", "");
    System.out.println(testMessage);
    String[] coinInfo = testMessage.split("},");
    
    for (int i = 0; i < coinInfo.length; i++) {
      String[] individualCoinInfo = coinInfo[i].split(",");
      String tempInfo = null;
      if (individualCoinInfo[12].contains("}")) {
        tempInfo = (individualCoinInfo[12].replace("}]", "").split("\":")[1]);
      } else tempInfo = (individualCoinInfo[12].split("\":")[1]);
      
      UpbitCoinDataDTO upbitCoinDataDTO = upbitCoinDataDTOBuilder(individualCoinInfo, tempInfo, coinName);
  
      System.out.println(upbitCoinDataDTO);
    }
    Assert.assertTrue(true);
  }
  
  public UpbitCoinDataDTO upbitCoinDataDTOBuilder(String[] individualCoinInfo, String tempInfo, String coinName) {
    UpbitCoinDataDTO coin = UpbitCoinDataDTO.builder()
            .coin(coinName)
            .market(individualCoinInfo[0].split("\":")[1])
            .candleDateTimeUtc(LocalDateTime.parse(individualCoinInfo[1].split("\":")[1].replace("\"", "")))
            .candleDateTimeKst(LocalDateTime.parse(individualCoinInfo[2].split("\":")[1].replace("\"", "")))
            .openingPrice(individualCoinInfo[3].split("\":")[1])
            .highPrice(individualCoinInfo[4].split("\":")[1])
            .lowPrice(individualCoinInfo[5].split("\":")[1])
            .tradePrice(individualCoinInfo[6].split("\":")[1])
            .timestamp(individualCoinInfo[7].split("\":")[1])
            .candleAccTradePrice(individualCoinInfo[8].split("\":")[1])
            .candleAccTradeVolume(individualCoinInfo[9].split("\":")[1])
            .prevClosingPrice(individualCoinInfo[10].split("\":")[1])
            .changePrice(individualCoinInfo[11].split("\":")[1])
            .changeRate(tempInfo)
            .build();
    return coin;
  }
}

