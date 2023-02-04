package com.crypto.crypto.web;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RestApi {
  @Test
  public void returnStatus() throws UnirestException {
    HttpResponse<String> string = null;
    HttpResponse<String> response = Unirest.get("https://api.upbit.com/v1/candles/days?market=krw-btc&count=1")
            .header("accept","application/json")
            .asString();
    System.out.println(response.getBody());
    Assertions.assertFalse(response.equals(string));
  }
}
