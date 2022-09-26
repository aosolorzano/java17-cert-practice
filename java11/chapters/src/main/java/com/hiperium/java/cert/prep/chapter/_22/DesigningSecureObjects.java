package com.hiperium.java.cert.prep.chapter._22;

import java.util.ArrayList;
import java.util.List;

/**
 * Java has a Cloneable interface that we can implement if we want classes to be able to call the "clone()" method on
 * our objects. This helps with making defensive copies.
 */
final class Animal implements Cloneable {

    // NOTE: Use ArrayList instead List for the use of the "clone()" method.
    private final ArrayList<String> favoriteFoods;

    public Animal() {
        this.favoriteFoods = new ArrayList<>();
        this.favoriteFoods.add("Apple");
    }
    /**
     * What's this about the last rule for creating immutable objects? Let's say we want to allow the user to provide
     * the favoriteFoods data, so we implement the following constructor:
     */
    public Animal(List<String> favoriteFoods) {
        if (null == favoriteFoods)
            throw new RuntimeException("Favorite foods is required.");
        // NOTE: A hacker is tricky, though, and he/she decides to send a "favoriteFood" object but keep his own secret
        // reference to it, which he can modify directly.
        // >>>> this.favoriteFoods = favoriteFoods;  <<<< BAD ASSIGMENT

        // The SOLUTION is to use a copy constructor to make a copy of the list object containing the same elements.
        // The copy operation is called a defensive copy because the copy is being made in case other code does
        // something unexpected.
        this.favoriteFoods = new ArrayList<>(favoriteFoods);
    }
    /**
     * We carefully followed the first three rules, but unfortunately, a hacker can modify our data by calling
     * "getFavoriteFoods().clear()" or add food to the list that our animal doesn't like. It's not an immutable object
     * if we can change it contents.
     */
    public List<String> getBadFavoriteFoods() {
        return favoriteFoods;
    }
    /**
     * If we don't have a getter for the favoriteFoods object, how do callers can access it? Simple, by using delegate
     * methods to read the data, as shown in the next getters meths:
     */
    public int getFavoriteFoodsCount() {
        return favoriteFoods.size();
    }
    public String getFavoriteFoodsElement(int index) {
        return favoriteFoods.get(index);
    }
    /**
     * Another option is to create a copy of the favoriteFoods object and return the copy anytime it is requested, so
     * the original remains safe.
     */
    public List<String> getFavoriteFoods() {
        return new ArrayList<>(this.favoriteFoods);
    }
    /**
     * We can write an implementation that does a deep copy and clones the objects inside. A deep copy does make a new
     * ArrayList object. Changes to the cloned object do not affect the original.
     */
    @Override
    public Animal clone() {
        ArrayList<String> listClone = (ArrayList<String>) favoriteFoods.clone();
        return new Animal(listClone);
    }
}

/**
 * IMPORTANT: We should be familiar with a common strategy for making a class immutable:
 *
 *      1. Mark the class as final.
 *      2. Mark all the instance variables private.
 *      3. Don't define any setter methods and make fields final.
 *      4. Don't allow referenced mutable objects to be modified.
 *      5. Use a constructor to set all properties of the object, making a copy if needed.
 */
public class DesigningSecureObjects {

    public static void main(String[] args) {
        try {
            cloningObjects();
        } catch (CloneNotSupportedException e) {
            System.out.println("Error trying to make a clone object: " + e.getMessage());
        }
    }

    private static void cloningObjects() throws CloneNotSupportedException {
        List<String> food = new ArrayList<>();
        food.add("grass");
        Animal sheep = new Animal(food);
        Animal clone = (Animal) sheep.clone();
        System.out.println("sheep == clone = " + (sheep == clone));                 // PRINT: false
        System.out.println("sheep.favoriteFoods == clone.favoriteFoods = " +
                (sheep.getFavoriteFoods() == clone.getFavoriteFoods()));            // PRINT: false
        System.out.println("sheep.favoriteFoods == clone.favoriteFoods = " +
                (sheep.getFavoriteFoods().equals(clone.getFavoriteFoods())));       // PRINT: true
    }

}
