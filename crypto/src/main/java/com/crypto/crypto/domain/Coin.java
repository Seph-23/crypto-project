package com.crypto.crypto.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Coin {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "coin_id")
  private Long id;

  private String name;

}
