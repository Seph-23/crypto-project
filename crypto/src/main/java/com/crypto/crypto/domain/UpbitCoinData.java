package com.crypto.crypto.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpbitCoinData {
  
  private String coin;
  private String market;
  private String candleDateTimeUtc;
  private String candleDateTimeKst;
  private String openingPrice;
  private String highPrice;
  private String lowPrice;
  private String tradePrice;
  private String timestamp;
  private String candleAccTradePrice;
  private String candleAccTradeVolume;
  private String prevClosingPrice;
  private String changePrice;
  private String changeRate;
}
