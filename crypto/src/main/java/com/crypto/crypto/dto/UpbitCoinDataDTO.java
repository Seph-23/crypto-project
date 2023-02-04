package com.crypto.crypto.dto;

import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UpbitCoinDataDTO {
  
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
