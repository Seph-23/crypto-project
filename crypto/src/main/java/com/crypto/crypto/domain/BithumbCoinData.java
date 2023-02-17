package com.crypto.crypto.domain;

import com.crypto.crypto.dto.BithumbCoinDataDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(BithumbCoinDataPK.class)
public class BithumbCoinData {

  @Id
  private String coin;

  @Id
  private LocalDateTime candleDateTime;

  private String openingPrice;
  private String tradePrice;
  private String highPrice;
  private String lowPrice;

  @Builder
  public BithumbCoinData(String coin, LocalDateTime candleDateTime, String openingPrice, String tradePrice,
    String highPrice, String lowPrice) {
    this.coin = coin;
    this.candleDateTime = candleDateTime;
    this.openingPrice = openingPrice;
    this.tradePrice = tradePrice;
    this.highPrice = highPrice;
    this.lowPrice = lowPrice;
  }

  public static BithumbCoinData createFromBithumbCoinDataDto(BithumbCoinDataDTO bithumbCoinDataDTO) {
    BithumbCoinData bithumbCoinData = BithumbCoinData.builder()
      .coin(bithumbCoinDataDTO.getCoin())
      .candleDateTime(bithumbCoinDataDTO.getCandleDateTime())
      .openingPrice(bithumbCoinDataDTO.getOpeningPrice())
      .tradePrice(bithumbCoinDataDTO.getTradePrice())
      .highPrice(bithumbCoinDataDTO.getHighPrice())
      .lowPrice(bithumbCoinDataDTO.getLowPrice())
      .build();
    return bithumbCoinData;
  }
}
