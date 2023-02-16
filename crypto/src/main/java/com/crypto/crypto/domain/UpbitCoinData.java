package com.crypto.crypto.domain;

import com.crypto.crypto.dto.UpbitCoinDataDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpbitCoinData {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String coin;
  private LocalDateTime candleDateTimeUtc;
  private String openingPrice;
  private String highPrice;
  private String lowPrice;
  private String tradePrice;
  private String timestamp;
  
  @Builder
  public UpbitCoinData(String coin, String market, LocalDateTime candleDateTimeUtc,
                       String openingPrice, String highPrice, String lowPrice,
                       String tradePrice, String timestamp) {
    this.coin = coin;
    this.candleDateTimeUtc = candleDateTimeUtc;
    this.openingPrice = openingPrice;
    this.highPrice = highPrice;
    this.lowPrice = lowPrice;
    this.tradePrice = tradePrice;
    this.timestamp = timestamp;
  }
  
  public static UpbitCoinData createFromUpbitCoinDataDTO(UpbitCoinDataDTO upbitCoinDataDTO){
    UpbitCoinData upbitCoinData = UpbitCoinData.builder()
            .coin(upbitCoinDataDTO.getCoin())
            .candleDateTimeUtc(upbitCoinDataDTO.getCandleDateTimeUtc())
            .openingPrice(upbitCoinDataDTO.getOpeningPrice())
            .highPrice(upbitCoinDataDTO.getHighPrice())
            .lowPrice(upbitCoinDataDTO.getLowPrice())
            .tradePrice(upbitCoinDataDTO.getTradePrice())
            .timestamp(upbitCoinDataDTO.getTimestamp())
            .build();
    return upbitCoinData;
  }
}
