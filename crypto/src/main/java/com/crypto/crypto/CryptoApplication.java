package com.crypto.crypto;


import com.crypto.crypto.web.BinanceAPI;
import com.crypto.crypto.web.BithumbAPI;
import com.crypto.crypto.web.UpbitAPI;
import java.util.stream.IntStream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CryptoApplication {
	
	//List of crypto names
	private static String[] coins = {
					"BTC", "ETH", "XRP", "ADA", "DOGE", "MATIC", "SOL", "DOT", "AVAX", "TRX", "ATOM", "LINK", "ETC",
					"BCH", "XLM", "ALGO", "VET", "MANA", "AAVE", "EOS", "AXS", "SAND", "THETA", "XTZ", "CHZ", "XEC",
					"BTT", "ENJ", "ZIL", "T", "1INCH", "BAT", "GMT", "TFUEL", "QTUM", "WAVES", "ANKR", "GLM", "JST",
					"ICX", "OMG", "ZRX", "HIVE", "ONT", "IOST", "WAXP", "SXP", "KNC", "PLA", "PUNDIX", "SNT", "ELF",
					"SRM", "POWR", "DAR"
	};

	public static void main(String[] args) {
		SpringApplication.run(CryptoApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner initData(BithumbAPI bithumbAPI) {
//		return args -> {
//			bithumbAPI.initBitumbCoinData(coins);
//		};
//	}
//
//	@Bean
//	public CommandLineRunner initData2(BinanceAPI binanceAPI) {
//		return args -> {
//			binanceAPI.buildHistory(coins);
//		};
//	}
//
//	@Bean
//	public CommandLineRunner initData3(UpbitAPI upbitAPI) {
//		return args -> {
//			upbitAPI.buildHistory(coins);
//		};
//	}

}
