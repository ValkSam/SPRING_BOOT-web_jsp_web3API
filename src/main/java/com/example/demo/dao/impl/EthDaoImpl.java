package com.example.demo.dao.impl;

import com.example.demo.dao.EthDao;
import com.example.demo.dao.exception.NotFountException;
import com.example.demo.model.BchAddress;
import com.example.demo.model.BchPayment;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.*;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

/**
 * Created by ValkSam on 11.06.2017.
 */
@Repository
public class EthDaoImpl implements EthDao {
  private List<BchAddress> addresses = new ArrayList<BchAddress>(){{
    add(new BchAddress("0x7f32960844E7487B7A3aa611Eb540e91614Db863".toLowerCase()));
  }};
  private Map<String, BchPayment> payments = new HashMap<>();
  private BigInteger lastBlockNumber = BigInteger.valueOf(1169945);

  @Override
  public void savePayment(BchPayment bchPayment){
    payments.put(bchPayment.getTransactionHash(), bchPayment);
    lastBlockNumber = bchPayment.getBlockNumber();
  }

  @Override
  public Optional<BchPayment> findPaymentByHash(String hash){
    return ofNullable(payments.get(hash));
  }

  @Override
  public List<BchPayment> getAllPaymentss(){
    return new ArrayList<BchPayment>(payments.values());
  }

  @Override
  public void setConformationByHash(String hash, BigInteger confirmations){
    BchPayment tx = ofNullable(payments.get(hash)).orElseThrow(()-> new NotFountException(hash));
    tx.setConfirmations(confirmations);
  }

  @Override
  public BigInteger getLastBlockNumber(){
    return lastBlockNumber;
  }

  @Override
  public Optional<BchAddress> findAddress(String address){
    return addresses.stream().filter(e->e.getAddress().toLowerCase().equals(address)).findFirst();
  }

}
