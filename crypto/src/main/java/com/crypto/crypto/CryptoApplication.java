package com.crypto.crypto;

import com.crypto.crypto.dto.BithumbCoinDataDTO;
import com.crypto.crypto.service.BithumbService;
import com.crypto.crypto.web.BithumbAPI;
import com.google.gson.Gson;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.stream.IntStream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

@SpringBootApplication
public class CryptoApplication {

	private static String[] coins = {"BTC", "ETH", "XRP", "ADA", "DOGE"};
	private static Gson gson = new Gson();

	public static void main(String[] args) {
		SpringApplication.run(CryptoApplication.class, args);
	}

	@Order(1)
	@Bean
	public CommandLineRunner initCoinData(BithumbAPI bithumbAPI, BithumbService bithumbService) {
		return args -> IntStream.range(0, 1).forEach(i -> {
			try {
				String data = bithumbAPI.getData(coins[i]);
				Map<String, Object> map = gson.fromJson(data, Map.class);	// map.get("status"), map.get("data")
				String dataStr = null;

				if (map.get("status").toString().equals("0000")) {				// 통신 성공시 (status code: 0000)
					dataStr = map.get("data").toString();
					dataStr = dataStr.replace("[", "");
					String[] coinData = dataStr.split("], ");

					for (String coinDatum : coinData) {
						String[] dailyData = coinDatum.split(", ");		// {기준 시간, 시가, 종가, 고가, 저가, 거래량}

						BithumbCoinDataDTO bithumbCoinDataDTO = bithumbCoinDataDtoFromStringArray(dailyData, i);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * 빗썸에서 제공하는 캔들 기준 시간(millisecond)를 LocalDateTime으로 변환하는 메서드
	 * @param milli
	 * @return LocalDateTime 캔들날짜
	 */
	public LocalDateTime milliToLocalDateTime(String milli) {
		String result = String.format("%.0f", Double.parseDouble(milli));

		return Instant.ofEpochMilli(Long.parseLong(result))
			.atZone(ZoneId.systemDefault())
			.toLocalDateTime();
	}

	public BithumbCoinDataDTO bithumbCoinDataDtoFromStringArray(String[] dailyData, int coinsIndex) {
		LocalDateTime day = milliToLocalDateTime(dailyData[0]);

		BithumbCoinDataDTO bithumbCoinDataDTO = BithumbCoinDataDTO.builder()		// 일별 데이터 DTO 생성
			.coin(coins[coinsIndex])
			.candleDateTime(day)
			.openingPrice(dailyData[1])
			.tradePrice(dailyData[2])
			.highPrice(dailyData[3])
			.lowPrice(dailyData[4])
			.candleAccTradeVolume(dailyData[5])
			.build();

		return bithumbCoinDataDTO;
	}
}
