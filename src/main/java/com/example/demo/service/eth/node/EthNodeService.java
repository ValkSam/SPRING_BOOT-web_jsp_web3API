package com.example.demo.service.eth.node;

import com.example.demo.model.BchBlock;
import com.example.demo.model.BchPayment;

import java.math.BigInteger;
import java.util.function.Consumer;

/**
 * Created by ValkSam on 11.06.2017.
 */

public interface EthNodeService {

  void subscribeForBlock(Consumer<BchBlock> consumer);

  void subscribeForTransaction(Consumer<BchPayment> consumer);

  void subscribeForMissedBlocks(BigInteger blockNumber, Consumer<BchBlock> consumer);
}
