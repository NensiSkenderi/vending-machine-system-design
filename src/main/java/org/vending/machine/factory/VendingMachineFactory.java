package org.vending.machine.factory;

import org.vending.machine.service.VendingMachineService;
import org.vending.machine.service.impl.VendingMachineServiceImpl;

public class VendingMachineFactory {

    public VendingMachineService createVendingMachine(){
        return new VendingMachineServiceImpl();
    }
}
