package com.crypto.crypto.controller;

import com.crypto.crypto.web.BinanceAPI;
import com.crypto.crypto.web.BithumbAPI;
import com.crypto.crypto.web.UpbitAPI;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class HomeController {

  //List of crypto names
  private static String[] coins = {
    "BTC", "ETH", "XRP", "ADA", "DOGE", "MATIC", "SOL", "DOT", "AVAX", "TRX", "ATOM", "LINK", "ETC",
    "BCH", "XLM", "ALGO", "VET", "MANA", "AAVE", "EOS", "AXS", "SAND", "THETA", "XTZ", "CHZ", "XEC",
    "BTT", "ENJ", "ZIL", "T", "1INCH", "BAT", "GMT", "TFUEL", "QTUM", "WAVES", "ANKR", "GLM", "JST",
    "ICX", "OMG", "ZRX", "HIVE", "ONT", "IOST", "WAXP", "SXP", "KNC", "PLA", "PUNDIX", "SNT", "ELF",
    "SRM", "POWR", "DAR"
  };

  private final BithumbAPI bithumbAPI;
  private final UpbitAPI upbitAPI;
  private final BinanceAPI binanceAPI;

  @GetMapping("/init/data")
  public String initData() {

//    bithumbAPI.initBitumbCoinData(coins);
//    upbitAPI.buildHistory(coins);
    binanceAPI.buildHistory(coins);

    return "Init Data";
  }
}