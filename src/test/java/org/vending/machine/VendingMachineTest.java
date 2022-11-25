package org.vending.machine;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.vending.machine.factory.Bucket;
import org.vending.machine.factory.VendingMachineFactory;
import org.vending.machine.model.Coin;
import org.vending.machine.model.VendingMachineItem;
import org.vending.machine.service.VendingMachineService;

import java.util.List;

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
        VendingMachineItem item = VendingMachineItem.COKE;
        long amount = vendingMachineService.selectItemAndGetPrice(item);
        assertEquals(item.getPrice(), amount);
        vendingMachineService.insertCoin(Coin.DIME);
        vendingMachineService.insertCoin(Coin.DIME);
        vendingMachineService.insertCoin(Coin.NICKLE);
        Bucket<VendingMachineItem, List<Coin>> bucket = vendingMachineService.collectItemAndChange();

        VendingMachineItem first = bucket.getFirst();
        List<Coin> second = bucket.getSecond();

        assertEquals(first.getName(), "coke");
        assertTrue(second.isEmpty());
    }

}
