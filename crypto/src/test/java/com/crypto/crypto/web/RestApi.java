package com.crypto.crypto.web;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RestApi {
  static final String[] coins = {"btc"};
  //, "eth", "xrp", "ada", "doge", "matic", "sol", "dot", "avax"
  static final HttpResponse<String> string = null;
  @Test
  public void returnStatus() throws Exception {
    for(int o = 0; o < coins.length; o++){
      String coin = coins[o];
      HttpResponse<String> response = Unirest.get("https://api.upbit.com/v1/candles/days?market=krw-"+ coin + "&count=200")
              .header("accept","application/json")
              .asString();
      //System.out.println(response.getStatus());
      //System.out.println(response.getBody());
      
      String testMessage = response.getBody();
      System.out.println(testMessage);
      testMessage = testMessage.replace('{', ' ');
      System.out.println(testMessage);
      String[] newMessage = testMessage.split("},", 200);
      for(int i = 0; i < newMessage.length; i++){
        System.out.println(newMessage[i]);
      }
      
    }
    Assertions.assertTrue(coins.equals(null));
  }
}
