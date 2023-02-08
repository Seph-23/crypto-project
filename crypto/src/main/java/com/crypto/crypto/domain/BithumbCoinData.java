package com.crypto.crypto.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
public class BithumbCoinData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime candleDateTime;
  private String openingPrice;
  private String tradePrice;
  private String highPrice;
  private String lowPrice;
  private String candleAccTradeVolume;

}
