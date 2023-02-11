package com.crypto.crypto.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BinanceRepositoryImpl implements BinanceRepositoryInterface {

  @PersistenceContext
  private final EntityManager em;

}
