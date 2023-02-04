package com.crypto.crypto.web;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import lombok.SneakyThrows;

public class RestApi {
  @SneakyThrows
  public void returnStatus(){
    HttpResponse<String> response = Unirest.get("https://api.upbit.com/v1/candles/days?count=1")
            .header("accept","application/json")
            .asString();
  }
}
