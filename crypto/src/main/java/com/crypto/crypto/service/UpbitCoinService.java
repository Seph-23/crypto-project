package com.crypto.crypto.service;

import com.crypto.crypto.domain.UpbitCoinData;
import com.crypto.crypto.dto.UpbitCoinDataDTO;
import com.crypto.crypto.repository.UpbitCoinDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UpbitCoinService {
  
  private final UpbitCoinDataRepository upbitCoinDataRepository;
  
  @Transactional
  public void addData(UpbitCoinDataDTO upbitCoinDataDTO){
    UpbitCoinData upbitCoinData = UpbitCoinData
            .createFromUpbitCoinDataDTO(upbitCoinDataDTO);
    upbitCoinDataRepository.save(upbitCoinData);
  }
}
