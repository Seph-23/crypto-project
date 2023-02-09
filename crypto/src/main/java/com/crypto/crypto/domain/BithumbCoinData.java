package com.crypto.crypto.domain;

import com.crypto.crypto.dto.BithumbCoinDataDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BithumbCoinData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String coin;
  private LocalDateTime candleDateTime;
  private String openingPrice;
  private String tradePrice;
  private String highPrice;
  private String lowPrice;
  private String candleAccTradeVolume;

  @Builder
  public BithumbCoinData(String coin, LocalDateTime candleDateTime, String openingPrice, String tradePrice,
    String highPrice, String lowPrice, String candleAccTradeVolume) {
    this.coin = coin;
    this.candleDateTime = candleDateTime;
    this.openingPrice = openingPrice;
    this.tradePrice = tradePrice;
    this.highPrice = highPrice;
    this.lowPrice = lowPrice;
    this.candleAccTradeVolume = candleAccTradeVolume;
  }

  public static BithumbCoinData createFromBithumbCoinDataDto(BithumbCoinDataDTO bithumbCoinDataDTO) {
    BithumbCoinData bithumbCoinData = BithumbCoinData.builder()
      .coin(bithumbCoinDataDTO.getCoin())
      .candleDateTime(bithumbCoinDataDTO.getCandleDateTime())
      .openingPrice(bithumbCoinDataDTO.getOpeningPrice())
      .tradePrice(bithumbCoinDataDTO.getTradePrice())
      .highPrice(bithumbCoinDataDTO.getHighPrice())
      .lowPrice(bithumbCoinDataDTO.getLowPrice())
      .candleAccTradeVolume(bithumbCoinDataDTO.getCandleAccTradeVolume())
      .build();
    return bithumbCoinData;
  }
}
