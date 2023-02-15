package com.crypto.crypto;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

public class TestCodes {
  
  @Test
  public void test(){
    System.out.println(Instant.now().toString().split("T")[0]);
    Assert.assertTrue(true);
  }
}
