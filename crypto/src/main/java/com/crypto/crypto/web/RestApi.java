package com.crypto.crypto.web;

import com.crypto.crypto.dto.UpbitCoinDataDTO;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import lombok.SneakyThrows;

public class RestApi {
  
  static final String[] coins = {"btc"};
  
  @SneakyThrows
  public void returnStatus() {
    for (int o = 0; o < coins.length; o++) {
      String coin = coins[o];
      HttpResponse<String> response = Unirest.get("https://api.upbit.com/v1/candles/days?market=krw-" + coin + "&count=200")
              .header("accept", "application/json")
              .asString();
      //System.out.println(response.getStatus());
      //System.out.println(response.getBody());
  
      String testMessage = response.getBody();
      System.out.println(testMessage);
      testMessage = testMessage.replace('{', ' ');
      System.out.println(testMessage);
      String[] newMessage = testMessage.split("},", 200);
      for (int i = 0; i < newMessage.length; i++) {
        UpbitCoinDataDTO newCoin = new UpbitCoinDataDTO();
        System.out.println(newMessage[i]);
        String[] information = newMessage[i].split(",", 13);
        newCoin.setCoin(coins[0]);
        newCoin.setMarket(information[0]);
        newCoin.setCandleDateTimeUtc(information[1]);
        newCoin.setCandleDateTimeKst(information[2]);
      }
    }
  }
}
