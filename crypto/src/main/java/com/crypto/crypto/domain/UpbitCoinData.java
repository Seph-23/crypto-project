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
  private String market;
  private LocalDateTime candleDateTimeUtc;
  private LocalDateTime candleDateTimeKst;
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
  
  @Builder
  public UpbitCoinData(String coin, String market, LocalDateTime candleDateTimeUtc, LocalDateTime candleDateTimeKst,
                       String openingPrice, String highPrice, String lowPrice, String tradePrice, String timestamp,
                       String candleAccTradePrice, String candleAccTradeVolume, String prevClosingPrice,
                       String changePrice, String changeRate) {
    this.coin = coin;
    this.market = market;
    this.candleDateTimeUtc = candleDateTimeUtc;
    this.candleDateTimeKst = candleDateTimeKst;
    this.openingPrice = openingPrice;
    this.highPrice = highPrice;
    this.lowPrice = lowPrice;
    this.tradePrice = tradePrice;
    this.timestamp = timestamp;
    this.candleAccTradePrice = candleAccTradePrice;
    this.candleAccTradeVolume = candleAccTradeVolume;
    this.prevClosingPrice = prevClosingPrice;
    this.changePrice = changePrice;
    this.changeRate = changeRate;
  }
  
  public static UpbitCoinData createFromUpbitCoinDataDTO(UpbitCoinDataDTO upbitCoinDataDTO){
    UpbitCoinData upbitCoinData = UpbitCoinData.builder()
            .coin(upbitCoinDataDTO.getCoin())
            .market(upbitCoinDataDTO.getMarket())
            .candleDateTimeUtc(upbitCoinDataDTO.getCandleDateTimeUtc())
            .candleDateTimeKst(upbitCoinDataDTO.getCandleDateTimeKst())
            .openingPrice(upbitCoinDataDTO.getOpeningPrice())
            .highPrice(upbitCoinDataDTO.getHighPrice())
            .lowPrice(upbitCoinDataDTO.getLowPrice())
            .tradePrice(upbitCoinDataDTO.getTradePrice())
            .timestamp(upbitCoinDataDTO.getTimestamp())
            .candleAccTradePrice(upbitCoinDataDTO.getCandleAccTradePrice())
            .candleAccTradeVolume(upbitCoinDataDTO.getCandleAccTradeVolume())
            .prevClosingPrice(upbitCoinDataDTO.getPrevClosingPrice())
            .changePrice(upbitCoinDataDTO.getChangePrice())
            .changeRate(upbitCoinDataDTO.getChangeRate())
            .build();
    return upbitCoinData;
  }
}
