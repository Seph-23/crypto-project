package com.crypto.crypto;

import com.crypto.crypto.web.BithumbAPI;
import com.crypto.crypto.web.UpbitAPI;
import java.util.stream.IntStream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

@SpringBootApplication
public class CryptoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoApplication.class, args);
	}

	@Order(1)
	@Bean
	public CommandLineRunner initCoinData(UpbitAPI upbitAPI, BithumbAPI bithumbAPI) {
		return args -> IntStream.rangeClosed(0, 1).forEach(i -> {
			String[] coins = {"BTC", "ETH", "XRP", "ADA", "DOGE"};
			bithumbAPI.getData(coins[i]);
		});
	}
}
