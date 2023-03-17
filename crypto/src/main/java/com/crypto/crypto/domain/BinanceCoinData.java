package com.crypto.crypto.domain;

import com.crypto.crypto.dto.BinanceCoinDataDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(BinanceCoinDataPK.class)
public class BinanceCoinData {

  @Id
  private String coin;

  @Id
  private LocalDateTime candleDateTime;

  private String highPrice;
  private String lowPrice;

  @Builder
  public BinanceCoinData(String coin, LocalDateTime candleDateTime, String highPrice,
    String lowPrice) {
    this.coin = coin;
    this.candleDateTime = candleDateTime;
    this.highPrice = highPrice;
    this.lowPrice = lowPrice;
  }

  public static BinanceCoinData createFromBinanceCoinDataDto(BinanceCoinDataDTO binanceCoinDataDTO) {
    BinanceCoinData binanceCoinData = BinanceCoinData.builder()
      .coin(binanceCoinDataDTO.getCoin())
      .candleDateTime(binanceCoinDataDTO.getCandleDateTime())
      .highPrice(binanceCoinDataDTO.getHighPrice())
      .lowPrice(binanceCoinDataDTO.getLowPrice())
      .build();
    return binanceCoinData;
  }
}
