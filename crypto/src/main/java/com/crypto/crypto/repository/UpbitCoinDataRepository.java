package com.crypto.crypto.repository;

import com.crypto.crypto.domain.UpbitCoinData;
import org.springframework.data.jpa.repository.JpaRepository;

//JPA Overriding page

public interface UpbitCoinDataRepository extends JpaRepository<UpbitCoinData, Long>, UpbitCoinDataRepositoryInterface {


}
