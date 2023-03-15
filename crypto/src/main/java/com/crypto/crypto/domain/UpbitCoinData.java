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

  private String highPrice;
  private String lowPrice;

  @Builder
  public UpbitCoinData(String coin, LocalDateTime candleDateTime, String highPrice, String lowPrice) {
    this.coin = coin;
    this.candleDateTime = candleDateTime;
    this.highPrice = highPrice;
    this.lowPrice = lowPrice;
  }
  
  public static UpbitCoinData createFromUpbitCoinDataDTO(UpbitCoinDataDTO upbitCoinDataDTO){
    UpbitCoinData upbitCoinData = UpbitCoinData.builder()
            .coin(upbitCoinDataDTO.getCoin())
            .candleDateTime(upbitCoinDataDTO.getCandleDateTime())
            .highPrice(upbitCoinDataDTO.getHighPrice())
            .lowPrice(upbitCoinDataDTO.getLowPrice())
            .build();
    return upbitCoinData;
  }
}
