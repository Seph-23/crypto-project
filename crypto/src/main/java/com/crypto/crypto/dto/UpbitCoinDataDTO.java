package com.crypto.crypto.dto;

import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
public class UpbitCoinDataDTO {
  
  private String coin;
  private LocalDateTime candleDateTimeUtc;
  private String openingPrice;
  private String highPrice;
  private String lowPrice;
  private String tradePrice;
  private String timestamp;
  
}
