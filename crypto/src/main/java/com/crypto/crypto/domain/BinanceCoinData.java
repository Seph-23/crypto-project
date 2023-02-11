package com.crypto.crypto.domain;

import com.crypto.crypto.dto.BinanceCoinDataDTO;
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
public class BinanceCoinData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String coin;
  private LocalDateTime openTime;
  private String openingPrice;
  private String highPrice;
  private String lowPrice;
  private String closePrice;
  private String volume;
  private LocalDateTime closeTime;
  private String quoteAssetVolume;
  private String numTrades;
  private String takerBuyBaseAssetVolume;
  private String takerBuyQuoteAssetVolume;

  @Builder
  public BinanceCoinData(String coin, LocalDateTime openTime, String openingPrice, String highPrice,
    String lowPrice, String closePrice, String volume, LocalDateTime closeTime,
    String quoteAssetVolume, String numTrades, String takerBuyBaseAssetVolume,
    String takerBuyQuoteAssetVolume) {
    this.coin = coin;
    this.openTime = openTime;
    this.openingPrice = openingPrice;
    this.highPrice = highPrice;
    this.lowPrice = lowPrice;
    this.closePrice = closePrice;
    this.volume = volume;
    this.closeTime = closeTime;
    this.quoteAssetVolume = quoteAssetVolume;
    this.numTrades = numTrades;
    this.takerBuyBaseAssetVolume = takerBuyBaseAssetVolume;
    this.takerBuyQuoteAssetVolume = takerBuyQuoteAssetVolume;
  }

  public static BinanceCoinData createFromBinanceCoinDataDto(BinanceCoinDataDTO binanceCoinDataDTO) {
    BinanceCoinData binanceCoinData = BinanceCoinData.builder()
      .coin(binanceCoinDataDTO.getCoin())
      .openTime(binanceCoinDataDTO.getOpenTime())
      .openingPrice(binanceCoinDataDTO.getOpeningPrice())
      .highPrice(binanceCoinDataDTO.getHighPrice())
      .lowPrice(binanceCoinDataDTO.getLowPrice())
      .closePrice(binanceCoinDataDTO.getClosePrice())
      .volume(binanceCoinDataDTO.getVolume())
      .closeTime(binanceCoinDataDTO.getCloseTime())
      .quoteAssetVolume(binanceCoinDataDTO.getQuoteAssetVolume())
      .numTrades(binanceCoinDataDTO.getNumTrades())
      .takerBuyBaseAssetVolume(binanceCoinDataDTO.getTakerBuyBaseAssetVolume())
      .takerBuyQuoteAssetVolume(binanceCoinDataDTO.getTakerBuyQuoteAssetVolume())
      .build();
    return binanceCoinData;
  }
}
