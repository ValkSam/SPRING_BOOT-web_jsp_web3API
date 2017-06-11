package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.web3j.protocol.core.methods.response.Transaction;

import java.math.BigInteger;

/**
 * Created by ValkSam on 11.06.2017.
 */
@Getter
@Setter
@ToString
public class BchPayment {
  private BigInteger blockNumber;
  private String blockHash;
  private String transactionHash;
  private String from;
  private String to;
  private String value;
  private BigInteger confirmations = BigInteger.ZERO;
}
