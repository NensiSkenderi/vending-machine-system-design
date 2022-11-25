package org.vending.machine;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.vending.machine.factory.Bucket;
import org.vending.machine.factory.VendingMachineFactory;
import org.vending.machine.model.Coin;
import org.vending.machine.model.VendingMachineItem;
import org.vending.machine.service.VendingMachineService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineTest {

    private static VendingMachineService vendingMachineService;

    @BeforeEach
    public void setUp(){
        vendingMachineService = VendingMachineFactory.createVendingMachine();
    }

    @BeforeAll
    public static void destroy(){
        vendingMachineService = null;
    }

    @Test
    public void shouldBuyItemWithExactPrice(){
        VendingMachineItem item = VendingMachineItem.COKE; // 25
        long amount = vendingMachineService.selectItemAndGetPrice(item);
        assertEquals(item.getPrice(), amount);
        vendingMachineService.insertCoin(Coin.DIME); // 10
        vendingMachineService.insertCoin(Coin.DIME); // 10
        vendingMachineService.insertCoin(Coin.NICKLE); // 5
        Bucket<VendingMachineItem, List<Coin>> bucket = vendingMachineService.collectItemAndChange();

        VendingMachineItem first = bucket.getFirst();
        List<Coin> second = bucket.getSecond();

        assertEquals(first.getName(), "coke");
        assertTrue(second.isEmpty());
    }

    @Test
    public void shouldGet2DimesChangeBack(){
        VendingMachineItem item = VendingMachineItem.PEPSI; // 30
        long amount = vendingMachineService.selectItemAndGetPrice(item);
        assertEquals(item.getPrice(), amount);
        vendingMachineService.insertCoin(Coin.QUARTER); // 25
        vendingMachineService.insertCoin(Coin.QUARTER); // 25

        Bucket<VendingMachineItem, List<Coin>> bucket = vendingMachineService.collectItemAndChange();
        VendingMachineItem first = bucket.getFirst();
        List<Coin> second = bucket.getSecond();

        assertEquals(first.getName(), "pepsi");
        assertFalse(second.isEmpty());
        assertEquals(second.get(0).name(), Coin.DIME.name());
        assertEquals(second.get(1).name(), Coin.DIME.name());
        assertEquals(second.stream().mapToInt(Coin::getCoinValue).sum(), 20);

    }
}
