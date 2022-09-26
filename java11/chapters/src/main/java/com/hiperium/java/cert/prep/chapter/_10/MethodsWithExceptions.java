package com.hiperium.java.cert.prep.chapter._10;

class CanNotHopException extends Exception {
}

class CanNotRoarException extends RuntimeException {
}

interface Roar {
    void roar() throws CanNotHopException;
}

class Hopper {
    public void hop() {
    }

    public void hop2() throws CanNotHopException {
    }

    public void hop3() throws Exception {
    }
}

public class MethodsWithExceptions extends Hopper implements Roar {

    // public void hop() throws CanNotHopException {} // DOES NOT COMPILE ==>> CANNOT ADD NEW CHECKED EXCEPTIONS.

    @Override
    public void hop() throws IllegalStateException, CanNotRoarException {
        System.out.println("We are free to throw any unchecked exceptions.");
    }

    @Override
    public void hop2() {
        System.out.println("We can declare fewer exceptions than the method declared in superclass.");
    }

    @Override
    public void roar() {
        System.out.println("We can declare fewer exceptions than the method declared in interface.");
    }

    @Override
    public void hop3() throws CanNotHopException {
        System.out.println("We are allowed to declare a subclass of an exception type declared in method signature.");
    }


}
