package com.hiperium.java.cert.prep.chapter._22;

final class FoodOrder {
    private String item;
    private int count;

    private FoodOrder(String item, int count) {
        setItem(item);
        setCount(count);
    }

    public FoodOrder getOrder(String item, int count) {
        return new FoodOrder(item, count);
    }

    public String getItem() { return item; }
    public void setItem(String item) { this.item = item; }
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
}

/**
 * MAKING CLASSES "FINAL":
 * Remember, making methods final is extra work. Security points out that we don't need to allow subclasses at all since
 * everything we need is in FoodOrder.
 *
 * MAKING CONSTRUCTORS "PRIVATE":
 * Security notes that another way of preventing or controlling subclasses is to make the constructor private. This
 * technique requires static factory methods to obtain the object. The "factory method" technique gives you more control
 * over the process of object creation.
 *
 * PROTECT THE SOURCE CODE:
 * With the bytecode, a hacker can decompile our code and get source code. It's not as well written as the code we wrote,
 * but it has equivalent information. Some people compile their projects with obfuscation tools to try to hide
 * implementation details. Obfuscation is the automated process of rewriting source code that purposely makes it more
 * difficult to read.
 */
public class SensitiveObjects {
}
