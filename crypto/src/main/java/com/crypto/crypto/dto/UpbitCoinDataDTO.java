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
  private LocalDateTime candleDateTime;
  private String highPrice;
  private String lowPrice;

}
