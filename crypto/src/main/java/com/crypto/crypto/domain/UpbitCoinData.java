package com.crypto.crypto.domain;

import com.crypto.crypto.dto.UpbitCoinDataDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(UpbitCoinDataPK.class)
public class UpbitCoinData {

  @Id
  private String coin;

  @Id
  private LocalDateTime candleDateTime;

  private String openingPrice;
  private String highPrice;
  private String lowPrice;
  private String tradePrice;
  private String timestamp;
  
  @Builder
  public UpbitCoinData(String coin, LocalDateTime candleDateTime,
                       String openingPrice, String highPrice, String lowPrice,
                       String tradePrice, String timestamp) {
    this.coin = coin;
    this.candleDateTime = candleDateTime;
    this.openingPrice = openingPrice;
    this.highPrice = highPrice;
    this.lowPrice = lowPrice;
    this.tradePrice = tradePrice;
    this.timestamp = timestamp;
  }
  
  public static UpbitCoinData createFromUpbitCoinDataDTO(UpbitCoinDataDTO upbitCoinDataDTO){
    UpbitCoinData upbitCoinData = UpbitCoinData.builder()
            .coin(upbitCoinDataDTO.getCoin())
            .candleDateTime(upbitCoinDataDTO.getCandleDateTime())
            .openingPrice(upbitCoinDataDTO.getOpeningPrice())
            .highPrice(upbitCoinDataDTO.getHighPrice())
            .lowPrice(upbitCoinDataDTO.getLowPrice())
            .tradePrice(upbitCoinDataDTO.getTradePrice())
            .timestamp(upbitCoinDataDTO.getTimestamp())
            .build();
    return upbitCoinData;
  }
}
