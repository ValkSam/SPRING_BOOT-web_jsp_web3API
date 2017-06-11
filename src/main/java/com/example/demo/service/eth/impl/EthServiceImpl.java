package com.example.demo.service.eth.impl;

import com.example.demo.dao.EthDao;
import com.example.demo.model.BchAddress;
import com.example.demo.model.BchBlock;
import com.example.demo.model.BchPayment;
import com.example.demo.service.eth.EthService;
import com.example.demo.service.eth.node.EthNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by ValkSam on 11.06.2017.
 */
@Service
public class EthServiceImpl implements EthService {
  @Autowired EthNodeService ethNodeService;

  @Autowired EthDao ethDao;

  @PostConstruct
  private void init() {
    Consumer<BchBlock> blockConsumer = new Consumer<BchBlock>() {
      @Override
      public void accept(BchBlock bchBlock) {
        System.out.println(bchBlock);
        List<BchPayment> trackedPayments = getBchPaymentList();
        for (BchPayment tx : trackedPayments) {
          BigInteger blockNumberAtPaymentCreationMoment = tx.getBlockNumber();
          BigInteger confirmations = bchBlock.getNumber().subtract(blockNumberAtPaymentCreationMoment);
          if (!confirmations.equals(tx.getConfirmations())) {
            ethDao.setConformationByHash(tx.getTransactionHash(), confirmations);
          }
        }
      }
    };
    ethNodeService.subscribeForBlock(blockConsumer);
    /**/
    Consumer<BchPayment> txConsumer = new Consumer<BchPayment>() {
      @Override
      public void accept(BchPayment bchPayment) {
        System.out.println(" >>>> new payment: " + bchPayment);
        Optional<BchAddress> address = ethDao.findAddress(bchPayment.getTo());
        if (address.isPresent()) {
          System.out.println(" >>>> new payment ADDED: " + bchPayment);
          ethDao.savePayment(bchPayment);
        }
      }
    };
    ethNodeService.subscribeForTransaction(txConsumer);
    /**/
    Consumer<BchBlock> missedBlockConsumer = new Consumer<BchBlock>() {
      @Override
      public void accept(BchBlock bchBlock) {
        System.out.println("******* missed: " + bchBlock);
        for (BchPayment bchPayment : bchBlock.getPayments()) {
          Optional<BchAddress> address = ethDao.findAddress(bchPayment.getTo());
          if (address.isPresent()) {
            String hash = bchPayment.getTransactionHash();
            Optional<BchPayment> payment = ethDao.findPaymentByHash(hash);
            if (!payment.isPresent()) {
              System.out.println("******* missed ADDED: " + bchPayment);
              ethDao.savePayment(bchPayment);
            }
          }
        }
      }
    };
    ethNodeService.subscribeForMissedBlocks(ethDao.getLastBlockNumber(), missedBlockConsumer);
  }

  @Override
  public List<BchPayment> getBchPaymentList() {
    return ethDao.getAllPaymentss();
  }

  @Override
  public BigInteger getLastBlockNumber() {
    return ethDao.getLastBlockNumber();
  }

}
