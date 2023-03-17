package com.crypto.crypto.repository;

import com.crypto.crypto.domain.BithumbCoinData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BithumbRepository extends JpaRepository<BithumbCoinData, String>,
  BithumbRepositoryInterface {

}
