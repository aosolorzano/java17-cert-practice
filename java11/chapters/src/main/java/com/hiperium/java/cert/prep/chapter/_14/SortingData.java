package com.hiperium.java.cert.prep.chapter._14;

import java.util.*;

class Duck implements Comparable<Duck> {
    private String name;
    public Duck(String name) {
        this.name = name;
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Duck)) return false;
        Duck duck = (Duck) o;
        return Objects.equals(this.name, duck.name);
    }
    @Override
    public String toString() {
        return this.name;
    }
    /**
     * SONAR LINT: Override "equals(Object obj)" to comply with the contract of the "compareTo(T o)" method.
     */
    @Override
    public int compareTo(Duck o) {
        return this.name.compareTo(o.name);             // Sorts ascending by name.
    }
}

class Animal implements Comparable<Animal> {
    private int id;
    public void setId(int id) { this.id = id; }
    @Override
    public int compareTo(Animal a) {
        // return Integer.compare(this.id, a.id);       VALID: We could have used this instead.
        return this.id - a.id;                          // It is for ascending order. For descending order: (a.id - id).
    }
}

/**
 * CHECKING FOR NULL:
 * When writing our own compare methods, we should check the data before comparing it.
 */
class MissingDuck implements Comparable<MissingDuck> {
    private String name;
    @Override
    public int compareTo(MissingDuck missingDuck) {
        if (Objects.isNull(missingDuck))
            throw new IllegalArgumentException("Poorly formed duck");
        if (Objects.isNull(this.name) && Objects.isNull(missingDuck.name))
            return 0;
        else if (Objects.isNull(this.name))         // Nulls first.
            return -1;
        else if (Objects.isNull(missingDuck.name))  // Nulls first.
            return 1;
        else return this.name.compareTo(missingDuck.name);
    }
}

/**
 * KEEPING compareTo() and equals() CONSISTENT:
 * We might be sorting Product objects by name, but names are not unique. Therefore, the return value of compareTo()
 * might not be 0 when comparing two equal Product objects, so this compareTo() method is NOT CONSISTENT with equals.
 * One way to fix that is to use a Comparator to define the sort elsewhere.
 */
class Product implements Comparable<Product> {
    private int id;
    private String name;
    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() { return id; }
    public int hashCode() { return id; }
    public boolean equals(Object obj) {
        if (!(obj instanceof Product)) return false;
        var other = (Product) obj;
        return this.id == other.id;
    }
    public String toString() { return this.name; }
    @Override
    public int compareTo(Product product) {
        return this.name.compareTo(product.name);
    }
}

class Squirrel {
    private int weight;
    private String species;
    public Squirrel(int weight, String species) {
        this.weight = weight;
        this.species = species;
    }
    public String toString() { return this.species; }
    public int getWeight() { return weight; }
    public String getSpecies() { return species; }
}

class Rabbit {
    int id;
    public Rabbit(int id) { this.id = id; }
    public String toString() { return String.valueOf(this.id); }
}

/**
 * We discussed “order” for the TreeSet and TreeMap classes. For numbers, order is obvious—it is numerical order.
 * For String objects, order is defined according to the Unicode character mapping.
 *
 * NOTE: numbers sort before letters, and uppercase letters sort before lowercase letters.
 */
public class SortingData {

    public static void main(String[] args) {
        sortingDucksCollection();
        sortingAnimalsCollection();
        sortingWithComparator();
        multiFieldComparator();
        sortingAndSearching();
    }

    public static void sortingDucksCollection() {
        var ducks = new ArrayList<Duck>();
        ducks.add(new Duck("Quack"));
        ducks.add(new Duck("Puddles"));
        Collections.sort(ducks);                // Sort by name
        System.out.println(ducks);              // [Puddles, Quack]
    }

    public static void sortingAnimalsCollection() {
        var a1 = new Animal();
        var a2 = new Animal();
        a1.setId(5);
        a2.setId(7);
        System.out.println(a1.compareTo(a2));   // -2
        System.out.println(a1.compareTo(a1));   // 0
        System.out.println(a2.compareTo(a1));   // 2
    }

    /**
     * Comparable and Comparator are in different packages, namely, java.lang versus java.util, respectively.
     */
    public static void sortingWithComparator() {
        var anonymousComparator = new Comparator<Product>() {                   // Anonymous inner Comparator.
            @Override public int compare(Product p1, Product p2) {
                return p1.getId() - p2.getId();
            }
        };
        var products = new ArrayList<Product>();
        products.add(new Product(1, "Quack"));
        products.add(new Product(2, "Puddles"));
        Collections.sort(products);
        System.out.println("Sort with Comparable by name: " + products);        // [Puddles, Quack]
        Collections.sort(products, anonymousComparator);
        System.out.println("Sort with Comparator by id: " + products);          // [Quack, Puddles]

        // var lambda = (p1,p2) -> (p1.getId()-p2.getId());                     ERROR: Cannot resolve method 'getId()'.
        // var lambda = (Product p1,Product p2) -> (p1.getId()-p2.getId());     ERROR: Cannot infer type.
        Comparator<Product> lambdaComparator = (p1,p2) -> (p1.getId()-p2.getId());
        Collections.sort(products, lambdaComparator);

        var methodReferenceComparator = Comparator.comparing(Product::getId);
        Collections.sort(products, methodReferenceComparator);
        System.out.println("Sort with method reference Comparator: " + products);
    }

    public static void multiFieldComparator() {
        var comparator1 = new Comparator<Squirrel>() {
            @Override
            public int compare(Squirrel s1, Squirrel s2) {
                int result = s1.getSpecies().compareTo(s2.getSpecies());
                if (result != 0) return result;
                return s1.getWeight() - s2.getWeight();
            }
        };
        var list = new ArrayList<Squirrel>();
        list.add(new Squirrel(10, "Squirrels100"));
        list.add(new Squirrel(15, "Squirrels500"));
        Collections.sort(list, comparator1);

        // We can use method references and build the comparator chain:
        var comparator2 = Comparator.comparing(Squirrel::getWeight)
                .thenComparing(Squirrel::getSpecies);
        list.add(new Squirrel(10, "Squirrels1010"));
        Collections.sort(list, comparator2);
        System.out.println("Sort using a comparator chain: " + list);           // [Squirrels100, Squirrels1010, Squirrels500]

        // We can set a descending order using the reversed() method:
        var comparator3 = Comparator.comparing(Squirrel::getSpecies).reversed();
        Collections.sort(list, comparator3);
        System.out.println("Sort using a reversed order comparator: " + list);  // [Squirrels500, Squirrels1010, Squirrels100]
    }

    public static void sortingAndSearching() {
        List<Rabbit> rabbitsList = new ArrayList<>();
        rabbitsList.add(new Rabbit(3));
        // Collections.sort(rabbitsList);           ERROR: Rabbit does not implement Comparable<T> interface.
        Comparator<Rabbit> rabbitComparator = (r1, r2) -> (r1.id - r2.id);
        rabbitsList.add(new Rabbit(0));
        rabbitsList.add(new Rabbit(4));
        Collections.sort(rabbitsList, rabbitComparator);
        System.out.println("Sorted rabbit ArrayList by custom Comparator: " + rabbitsList);     // [0, 3, 4]

        // USING TREE-SET
        Set<Duck> ducks = new TreeSet<>();
        ducks.add(new Duck("Quack"));
        ducks.add(new Duck("Puddles"));
        System.out.println("Natural ordering of Ducks with TreeSet: " + ducks);                 // [Puddles, Quack]

        Set<Rabbit> rabbitsSet = new TreeSet<>();
        // rabbitsSet.add(new Rabbit());            ERROR: ClassCastException at RT. Rabbit does not implement Comparable.

        // We can tell to collections that require sorting, that we want to use a specific Comparator.
        rabbitsSet = new TreeSet<>((r1, r2) -> (r1.id - r2.id));
        rabbitsSet.add(new Rabbit(10));
        rabbitsSet.add(new Rabbit(7));
        rabbitsSet.add(new Rabbit(3));
        System.out.println("Sorted rabbit TreeSet by custom Comparator: " + rabbitsSet);        // [3, 7, 10]
    }
}
