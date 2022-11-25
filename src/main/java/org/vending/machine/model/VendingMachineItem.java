package org.vending.machine.model;

public enum VendingMachineItem {

    COKE("coke",25),
    PEPSI("pepsi",30),
    SODA("soda", 10),
    WATER("water", 7);

    private String name;
    private int price;

    VendingMachineItem(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
