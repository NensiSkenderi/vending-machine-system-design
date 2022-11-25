package org.vending.machine.service;

import org.vending.machine.factory.Bucket;
import org.vending.machine.model.Coin;
import org.vending.machine.model.VendingMachineItem;

import java.util.List;

public interface VendingMachineService {

    List<Coin> refund();
    long selectItemAndGetPrice(VendingMachineItem item);
    void reset();

    void insertCoin(Coin coin);

    Bucket<VendingMachineItem, List<Coin>> collectItemAndChange();

}
