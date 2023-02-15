package com.crypto.crypto;

import com.crypto.crypto.dto.BinanceCoinDataDTO;
import com.crypto.crypto.dto.BithumbCoinDataDTO;
import com.crypto.crypto.dto.UpbitCoinDataDTO;
import com.crypto.crypto.service.BinanceService;
import com.crypto.crypto.service.BithumbService;
import com.crypto.crypto.service.UpbitService;
import com.crypto.crypto.web.BinanceAPI;
import com.crypto.crypto.web.BithumbAPI;
import com.crypto.crypto.web.UpbitAPI;
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

@SpringBootApplication
public class CryptoApplication {

	private static Gson gson = new Gson();
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

	@Bean
	public CommandLineRunner initBinanceCoinData(BinanceAPI binanceAPI, BinanceService binanceService) {
		return args -> IntStream.range(0, coins.length).forEach(i -> {
			try {
				
				LocalDateTime localDateTime = LocalDateTime.parse("2023-02-10T00:00:00");
				String date = String.valueOf(localDateTime.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli());
				
				while(true){
					String data = binanceAPI.getData(coins[i], date);
					
					
					data = data.replace("[[", "[");
					data = data.replace("]]", "]");
					
					String[] coinData = data.split("],");
					
					if(date.equals(coinData[0].split(",")[0].replace("[", "")) || data.length() < 3 ){
						System.out.println("done");
						break;
					}
					
					date = coinData[0].split(",")[0].replace("[", "");
					
					for (int j = 0; j < coinData.length; j++) {
						coinData[j] = coinData[j].replace("[", "");
						coinData[j] = coinData[j].replace("]", "");
						coinData[j] = coinData[j].replace("\"", "");
						String[] dailyData = coinData[j].split(",");
						BinanceCoinDataDTO binanceCoinDataDTO = binanceCoinDataDtoFromStringArray(dailyData, i);
						
						binanceService.addData(binanceCoinDataDTO);
					}
				}
				
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * 빗썸 과거 데이터 컬렉터
	 * @param bithumbAPI
	 * @param bithumbService
	 * @return
	 */
	@Bean
	public CommandLineRunner initBithumbCoinData(BithumbAPI bithumbAPI, BithumbService bithumbService) {
		return args -> IntStream.range(0, coins.length).forEach(i -> {
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
						bithumbService.addData(bithumbCoinDataDTO);
					}
				}
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * 빗썸에서 제공하는 캔들 기준 시간(millisecond)를 LocalDateTime으로 변환하는 메서드.
	 * @param milli
	 * @return LocalDateTime 캔들날짜
	 */
	public LocalDateTime milliToLocalDateTime(String milli) {
		String result = String.format("%.0f", Double.parseDouble(milli));

		return Instant.ofEpochMilli(Long.parseLong(result))
			.atZone(ZoneId.of("UTC"))
			.toLocalDateTime();
	}

	/**
	 * 빗썸에서 추출한 일별 코인 데이터를 DTO로 변환하는 메서드.
	 * @param dailyData
	 * @param coinsIndex
	 * @return BithumbCoinDataDTO
	 */
	public BithumbCoinDataDTO bithumbCoinDataDtoFromStringArray(String[] dailyData, int coinsIndex) {
		LocalDateTime day = milliToLocalDateTime(dailyData[0]);

		if (dailyData[5].contains("]]")) {
			dailyData[5] = dailyData[5].replace("]]", "");
		}

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

	public BinanceCoinDataDTO binanceCoinDataDtoFromStringArray(String[] dailyData, int coinsIndex) {
		LocalDateTime openTime = milliToLocalDateTime(dailyData[0]);
		LocalDateTime closeTime = milliToLocalDateTime(dailyData[6]);

		BinanceCoinDataDTO binanceCoinDataDTO = BinanceCoinDataDTO.builder()
			.coin(coins[coinsIndex])
			.openTime(openTime)
			.openingPrice(dailyData[1])
			.highPrice(dailyData[2])
			.lowPrice(dailyData[3])
			.closePrice(dailyData[4])
			.volume(dailyData[5])
			.closeTime(closeTime)
			.quoteAssetVolume(dailyData[7])
			.numTrades(dailyData[8])
			.takerBuyBaseAssetVolume(dailyData[9])
			.takerBuyQuoteAssetVolume(dailyData[10])
			.build();

		return binanceCoinDataDTO;
	}
}
