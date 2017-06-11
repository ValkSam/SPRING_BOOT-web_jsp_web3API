package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by ValkSam on 11.06.2017.
 */
@Getter
@Setter
@ToString
public class BchBlock {
  BigInteger number;
  String hash;
  Integer transactionCount;
  List<BchPayment> payments;
}
