package com.crypto.crypto;

import com.crypto.crypto.dto.UpbitCoinDataDTO;
import com.crypto.crypto.service.UpbitCoinService;
import com.crypto.crypto.web.UpbitApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
public class CryptoApplication {
	
	//List of crypto names
	private static String[] coins = {
					"BTC", "ETH", "XRP", "ADA", "DOGE", "MATIC", "SOL", "DOT", "AVAX", "TRX", "ATOM", "LINK", "ETC",
					"BCH", "XLM", "ALGO", "VET", "MANA", "AAVE", "EOS", "AXS", "SAND", "THETA", "XTZ", "CHZ", "XEC",
					"BTT", "ENJ", "ZIL", "T", "1INCH", "BAT", "GMT", "TFUEL", "QTUM", "WAVES", "ANKR", "GLM", "JST",
					"ICX", "OMG", "ZRX", "HIVE", "ONT", "IOST", "WAXP", "SXP", "KNC", "PLA", "PUNDIX", "SNT", "ELF",
					"SRM", "ONG", "POWR", "DAR"
	};
	
	public String date = "2023-02-09";
	
	public static void main(String[] args) {
		SpringApplication.run(CryptoApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner initCoinData(UpbitApi upbitApi, UpbitCoinService upbitCoinService){
		return args -> IntStream.range(0,coins.length).forEach(i ->{
			boolean repeat = true;
			
			while(repeat){
				String[] coinInfo = upbitApi.getCoinHistory(coins[i], date);
				saveCoin(coinInfo, coins[i], upbitCoinService);
				repeatSearch(coinInfo, date);
				date = parseDate(coinInfo);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	//Method for saving crypto coin history
	public void saveCoin(String[] coinInfo, String coinName, UpbitCoinService upbitCoinService){
		for (int i = 0; i < coinInfo.length; i++) {
			String[] individualCoinInfo = coinInfo[i].split(",");
			String tempInfo = null;
			if (individualCoinInfo[12].contains("}")) {
				tempInfo = (individualCoinInfo[12].replace("}]", "").split("\":")[1]);
			} else tempInfo = (individualCoinInfo[12].split("\":")[1]);
			
			UpbitCoinDataDTO upbitCoinDataDTO = upbitCoinDataDTOBuilder(individualCoinInfo, tempInfo, coinName);
			
			upbitCoinService.addData(upbitCoinDataDTO);
		}
	}
	
	public boolean repeatSearch(String[] coinInfo, String date){
		boolean repeat = true;
		
		String lastDate = parseDate(coinInfo);
		
		if(lastDate.equals(date)){
			repeat = false;
		}
		
		return repeat;
	}
	
	public String parseDate(String[] coinInfo){
		String lastSearch = coinInfo[coinInfo.length-1];
		String lastCoinDate = lastSearch.split(",")[2];
		String lastKst = lastCoinDate.split("\":\"")[1].replace("\"", "");
		String lastDate = lastKst.split("T")[0];
		return lastDate;
	}
	
	//Method for setting the entity
	public UpbitCoinDataDTO upbitCoinDataDTOBuilder(String[] individualCoinInfo, String tempInfo, String coinName){
		UpbitCoinDataDTO coin = UpbitCoinDataDTO.builder()
						.coin(coinName)
						.market(individualCoinInfo[0].split("\":")[1])
						.candleDateTimeUtc(LocalDateTime.parse(individualCoinInfo[1].split("\":")[1].replace("\"", "")))
						.candleDateTimeKst(LocalDateTime.parse(individualCoinInfo[2].split("\":")[1].replace("\"", "")))
						.openingPrice(individualCoinInfo[3].split("\":")[1])
						.highPrice(individualCoinInfo[4].split("\":")[1])
						.lowPrice(individualCoinInfo[5].split("\":")[1])
						.tradePrice(individualCoinInfo[6].split("\":")[1])
						.timestamp(individualCoinInfo[7].split("\":")[1])
						.candleAccTradePrice(individualCoinInfo[8].split("\":")[1])
						.candleAccTradeVolume(individualCoinInfo[9].split("\":")[1])
						.prevClosingPrice(individualCoinInfo[10].split("\":")[1])
						.changePrice(individualCoinInfo[11].split("\":")[1])
						.changeRate(tempInfo)
						.build();
		return coin;
	}
}
