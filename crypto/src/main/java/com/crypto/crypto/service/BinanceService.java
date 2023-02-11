package com.crypto.crypto.service;

import com.crypto.crypto.domain.BinanceCoinData;
import com.crypto.crypto.dto.BinanceCoinDataDTO;
import com.crypto.crypto.repository.BinanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BinanceService {

  private final BinanceRepository binanceRepository;

  @Transactional
  public void addData(BinanceCoinDataDTO binanceCoinDataDTO) {
    BinanceCoinData binanceCoinData = BinanceCoinData.createFromBinanceCoinDataDto(
      binanceCoinDataDTO);
    binanceRepository.save(binanceCoinData);
  }
}
