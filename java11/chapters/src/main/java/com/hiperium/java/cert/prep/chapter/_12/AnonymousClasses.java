package com.hiperium.java.cert.prep.chapter._12;

interface SaleTodayInterface {
    int dollarsOff();
}

abstract class SaleTodayAbstract {
    abstract int dollarsOff();
}

public class AnonymousClasses {

    public static void main(String[] args) {
        AnonymousClasses anonymous = new AnonymousClasses();
        System.out.println("Admission price one: " + anonymous.admissionOne(20));
        System.out.println("Admission price two: " + anonymous.admissionTwo(17));
        System.out.println("Admission price three: " + anonymous.pay(15));
    }

    public int admissionOne(int basePrice) {
        SaleTodayAbstract saleTodayAbstract = new SaleTodayAbstract() {
            @Override
            int dollarsOff() {
                return 3;
            }
        }; // DO NOT FORGET THE SEMICOLON
        return basePrice - saleTodayAbstract.dollarsOff();
    }

    public int admissionTwo(int basePrice) {
        // SaleTodayInterface saleTodayInterface = () -> 5;  ==>> IT CAN BE IMPLEMENTED USING LAMBDAS.
        SaleTodayInterface saleTodayInterface = new SaleTodayInterface() {
            @Override
            public int dollarsOff() { // IT MUST REQUIRE PUBLIC ACCESS MODIFIER.
                return 5;
            }
        }; // DO NOT FORGET THE SEMICOLON
        return basePrice - saleTodayInterface.dollarsOff();
    }

    private int admissionThree(int basePrice, SaleTodayInterface sale) {
        return basePrice - sale.dollarsOff();
    }

    public int pay(int basePrice) {
        // return this.admissionThree(basePrice, () -> 7);  ==>> IT CAN BE IMPLEMENTED USING LAMBDAS.
        return this.admissionThree(basePrice, new SaleTodayInterface() {
            @Override
            public int dollarsOff() {
                return 7;
            }
        }); // DO NOT FORGET THE SEMICOLON

    }
}
