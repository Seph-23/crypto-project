package com.crypto.crypto.web;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class UpbitApi {
  
  //Method for basic parsing of json response
  @SneakyThrows
  public String[] getCoinHistory(String coinName, String date) {
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
  
  public String[] parseData(HttpResponse<String> response){
    String testMessage = response.getBody();
    testMessage = testMessage.replace("{", "");
    testMessage = testMessage.replace("[", "");
//    System.out.println(testMessage);
    String[] coinInfo = testMessage.split("},");
    return coinInfo;
  }

}
