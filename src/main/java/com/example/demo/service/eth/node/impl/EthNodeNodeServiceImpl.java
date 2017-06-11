package com.example.demo.service.eth.node.impl;

import com.example.demo.model.BchBlock;
import com.example.demo.model.BchPayment;
import com.example.demo.service.eth.node.EthNodeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.observers.Observers;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by ValkSam on 11.06.2017.
 */
@Component
@PropertySource(value = {
    "classpath:/eth.properties"
})
public class EthNodeNodeServiceImpl implements EthNodeService {
  @Value("${eth.uri}")
  private String uri;

  private Web3j web3j;
  private List<Subscription> blockSubscription = new ArrayList<>();
  private Subscription txSubscription;
  private Subscription missedTxSubscription;

  @PostConstruct
  private void init() {
    System.out.println("Connect to: " + uri);
    web3j = Web3j.build(new HttpService(uri));
  }

  @Override
  public void subscribeForBlock(Consumer<BchBlock> consumer) {
    Action1<EthBlock> action = new Action1<EthBlock>() {
      @Override
      public void call(EthBlock ethBlock) {
        BchBlock bchBlock = new BchBlock();
        bchBlock.setNumber(ethBlock.getBlock().getNumber());
        bchBlock.setHash(ethBlock.getBlock().getHash());
        bchBlock.setTransactionCount(ethBlock.getBlock().getTransactions().size());
        System.out.println("================> "+missedTxSubscription.isUnsubscribed());
        consumer.accept(bchBlock);
      }
    };
    Observer observer = Observers.create(action);
    blockSubscription.add(web3j.blockObservable(false).subscribe(action));
  }

  @Override
  public void subscribeForTransaction(Consumer<BchPayment> consumer) {
    Action1<Transaction> action = new Action1<Transaction>() {
      @Override
      public void call(Transaction tx) {
        BchPayment bchPayment = buildBchPayment(tx);
        consumer.accept(bchPayment);
      }
    };
    Observer observer = Observers.create(action);
    blockSubscription.add(web3j.transactionObservable().subscribe(action));
  }

  @Override
  public void subscribeForMissedBlocks(BigInteger blockNumber, Consumer<BchBlock> consumer) {
    Action1<EthBlock> action = new Action1<EthBlock>() {
      @Override
      public void call(EthBlock ethBlock) {
        BchBlock bchBlock = new BchBlock();
        bchBlock.setNumber(ethBlock.getBlock().getNumber());
        bchBlock.setHash(ethBlock.getBlock().getHash());
        bchBlock.setTransactionCount(ethBlock.getBlock().getTransactions().size());
        bchBlock.setPayments(ethBlock.getBlock().getTransactions().stream()
            .map(e -> buildBchPayment((EthBlock.TransactionObject) e))
            .collect(Collectors.toList()));
        consumer.accept(bchBlock);
      }
    };
    rx.Observable<EthBlock> observable = web3j.catchUpToLatestBlockObservable(
        DefaultBlockParameter.valueOf(blockNumber),
        true);
    missedTxSubscription = observable.subscribe(action);
    /*после завершения работы catchUpToLatestBlockObservable, missedTxSubscription автоматически отпишется  */
  }

  @PreDestroy
  private void destroy() {
    System.out.println("distroyed");
    for (Subscription subscription : blockSubscription) {
      if (!subscription.isUnsubscribed()) {
        subscription.unsubscribe();
      }
    }
    if (txSubscription != null && !txSubscription.isUnsubscribed()) {
      txSubscription.unsubscribe();
    }
    if (missedTxSubscription != null && !missedTxSubscription.isUnsubscribed()) {
      missedTxSubscription.unsubscribe();
    }
  }

  private BchPayment buildBchPayment(Transaction tx) {
    BchPayment bchPayment = new BchPayment();
    bchPayment.setBlockNumber(tx.getBlockNumber());
    bchPayment.setBlockHash(tx.getBlockHash());
    bchPayment.setTransactionHash(tx.getHash());
    bchPayment.setValue(tx.getValue().toString());
    bchPayment.setFrom(tx.getFrom());
    bchPayment.setTo(tx.getTo());
    return bchPayment;
  }


}
