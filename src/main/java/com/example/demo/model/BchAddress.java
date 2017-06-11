package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

/**
 * Created by ValkSam on 11.06.2017.
 */
@Getter
@Setter
@ToString
public class BchAddress implements Comparable<BchAddress> {
  private String address;

  public BchAddress(String address) {
    this.address = address;
  }

  @Override
  public int compareTo(BchAddress o) {
    return this.address.compareTo(o.address);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    BchAddress that = (BchAddress) o;

    return address != null ? address.equals(that.address) : that.address == null;

  }

  @Override
  public int hashCode() {
    return address != null ? address.hashCode() : 0;
  }
}
