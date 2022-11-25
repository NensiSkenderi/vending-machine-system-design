package org.vending.machine.factory;

import org.vending.machine.service.VendingMachineService;
import org.vending.machine.service.impl.VendingMachineServiceImpl;

public class VendingMachineFactory {
    public static VendingMachineService createVendingMachine(){
        return new VendingMachineServiceImpl();
    }
}
