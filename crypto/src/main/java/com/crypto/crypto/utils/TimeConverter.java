package com.crypto.crypto.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeConverter {

  /**
   * 기준 시간(millisecond)를 LocalDateTime으로 변환하는 메서드.
   * @param milli
   * @return LocalDateTime 캔들날짜
   */
  public static LocalDateTime milliToLocalDateTime(String milli) {
    String result = String.format("%.0f", Double.parseDouble(milli));

    return Instant.ofEpochMilli(Long.parseLong(result))
      .atZone(ZoneId.of("UTC"))
      .toLocalDateTime();
  }
}
