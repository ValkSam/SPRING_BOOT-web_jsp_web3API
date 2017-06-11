package com.example.demo.service.eth;

import com.example.demo.model.BchPayment;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by ValkSam on 11.06.2017.
 */
public interface EthService {
  List<BchPayment> getBchPaymentList();

  BigInteger getLastBlockNumber();
}
