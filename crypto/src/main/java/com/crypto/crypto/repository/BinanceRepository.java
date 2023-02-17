package com.crypto.crypto.repository;

import com.crypto.crypto.domain.BinanceCoinData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BinanceRepository extends JpaRepository<BinanceCoinData, String>,
  BinanceRepositoryInterface {

}
