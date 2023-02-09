package com.crypto.crypto.service;

import com.crypto.crypto.domain.BithumbCoinData;
import com.crypto.crypto.dto.BithumbCoinDataDTO;
import com.crypto.crypto.repository.BithumbRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BithumbService {

  private final BithumbRepository bithumbRepository;

  @Transactional
  public void addData(BithumbCoinDataDTO bithumbCoinDataDTO) {
    BithumbCoinData bithumbCoinData = BithumbCoinData
      .createFromBithumbCoinDataDto(bithumbCoinDataDTO);
    bithumbRepository.save(bithumbCoinData);
  }

}
