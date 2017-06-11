package com.example.demo.dao;

import com.example.demo.model.BchAddress;
import com.example.demo.model.BchPayment;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Created by ValkSam on 11.06.2017.
 */
public interface EthDao {
  void savePayment(BchPayment bchPayment);

  Optional<BchPayment> findPaymentByHash(String hash);

  List<BchPayment> getAllPaymentss();

  void setConformationByHash(String hash, BigInteger confirmations);

  BigInteger getLastBlockNumber();

  Optional<BchAddress> findAddress(String address);
}
