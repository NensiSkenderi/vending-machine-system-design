package org.vending.machine.service.impl;

import org.vending.machine.exception.NotFullyPaidexception;
import org.vending.machine.exception.NotSufficientChangeException;
import org.vending.machine.exception.SoldOutException;
import org.vending.machine.factory.Bucket;
import org.vending.machine.factory.Inventory;
import org.vending.machine.model.Coin;
import org.vending.machine.model.VendingMachineItem;
import org.vending.machine.service.VendingMachineService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class VendingMachineServiceImpl implements VendingMachineService {

    private final Inventory<Coin> cashInventory = new Inventory<>();
    private final Inventory<VendingMachineItem> itemInventory = new Inventory<>();
    private long totalSales;
    private VendingMachineItem currentItem;
    private long currentBalance;

    public VendingMachineServiceImpl() {
        initialize();
    }

    private void initialize() {
        Arrays.stream(Coin.values()).forEach(coin -> cashInventory.put(coin, 5));
        Arrays.stream(VendingMachineItem.values()).forEach(item -> itemInventory.put(item, 5));
    }

    @Override
    public List<Coin> refund() {
        List<Coin> refund = getChange(currentBalance); // 30
        updateCashInventory(refund);
        currentBalance = 0;
        currentItem = null;
        return refund;
    }

    @Override
    public long selectItemAndGetPrice(VendingMachineItem item) {
        if (itemInventory.hasItem(item)) {
            currentItem = item;
            return currentItem.getPrice();
        }
        throw new SoldOutException("Sold out, please try another item");
    }

    @Override
    public void reset() {
        cashInventory.clear();
        itemInventory.clear();
        totalSales = 0;
        currentItem = null;
        currentBalance = 0;
    }

    @Override
    public void insertCoin(Coin coin) {
        currentBalance = currentBalance + coin.getCoinValue();
        cashInventory.add(coin);
    }

    @Override
    public Bucket<VendingMachineItem, List<Coin>> collectItemAndChange() {
        VendingMachineItem item = collectItem();
        totalSales = totalSales + currentItem.getPrice();
        List<Coin> coins = collectChange();

        return new Bucket<>(item, coins);
    }

    private List<Coin> collectChange() {
        long changeAmount = currentBalance - currentItem.getPrice();
        List<Coin> changes = getChange(changeAmount);
        updateCashInventory(changes);
        currentBalance = 0;
        currentItem = null;
        return changes;
    }

    private void updateCashInventory(List<Coin> changes) {
        changes.forEach(cashInventory::deduct);
    }

    private VendingMachineItem collectItem() {
        if (isFullyPaid()) {
            if (hasSufficientChange()) {
                itemInventory.deduct(currentItem);
                return currentItem;
            }

            throw new NotSufficientChangeException("Not sufficient change");
        }

        long remainingBalance = currentItem.getPrice() - currentBalance;
        throw new NotFullyPaidexception("Price not paid, remaining balance is: " + remainingBalance);
    }

    private boolean hasSufficientChange() {
        long amount = currentBalance - currentItem.getPrice(); // 20 - 7 = 13
        try {
            getChange(amount);
        }
        catch (NotSufficientChangeException e){
            return false;
        }
        return true;
    }

    private boolean isFullyPaid() {
        return currentBalance >= currentItem.getPrice();
    }

    private List<Coin> getChange(long amount) {
        List<Coin> changes = Collections.emptyList();
        if (amount > 0) {
            changes = new ArrayList<>();
            long balance = amount;

            while (balance > 0) {
                if (balance >= Coin.QUARTER.getCoinValue() && cashInventory.hasItem(Coin.QUARTER)) {
                    changes.add(Coin.QUARTER);
                    balance = balance - Coin.QUARTER.getCoinValue();
                } else if (balance >= Coin.DIME.getCoinValue() && cashInventory.hasItem(Coin.DIME)) {
                    changes.add(Coin.DIME);
                    balance = balance - Coin.DIME.getCoinValue();
                } else if (balance >= Coin.NICKLE.getCoinValue() && cashInventory.hasItem(Coin.NICKLE)) {
                    changes.add(Coin.NICKLE);
                    balance = balance - Coin.NICKLE.getCoinValue();
                } else if (balance >= Coin.PENNY.getCoinValue() && cashInventory.hasItem(Coin.PENNY)) {
                    changes.add(Coin.PENNY);
                    balance = balance - Coin.PENNY.getCoinValue();
                }
                else throw new NotSufficientChangeException("Try another product");
            }
        }
        return changes;
    }

    public long getTotalSales(){
        return totalSales;
    }
}
