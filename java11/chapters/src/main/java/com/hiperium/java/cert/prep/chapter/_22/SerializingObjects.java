package com.hiperium.java.cert.prep.chapter._22;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

final class Employee implements Serializable {
    private static final Map<Integer, Employee> pool = new ConcurrentHashMap<>();

    private int id;
    private String name;
    // Our zoo has decided that "address" information is sensitive and shouldn't be written to disk.
    private transient String address;
    private int age;
    private String ssn;     // Social Security Number (SSN)

    public Employee(int id, String name, int age, String ssn, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.ssn = ssn;
        this.address = address;
    }
    // We can specify fields to be serialized in an array. We can think of serialPersistentFields as the opposite of
    // transient, like the "address" field. The former is a whitelist of fields that should be serialized, while the
    // latter is a blacklist of fields that should not. It must be declared "private static final".
    private static final ObjectStreamField[] serialPersistenceFields = {
            new ObjectStreamField("id", Integer.class),
            new ObjectStreamField("name", String.class),
            new ObjectStreamField("ssn", String.class)};

    private String encrypt(String input) {
        // Implementation omitted
        return "Encrypted<".concat(input).concat(">");
    }
    private String decrypt(String encryptedInput) {
        // Implementation omitted
        return encryptedInput;
    }

    /**
     * Security may demand custom serialization. In our case, we got a new requirement to add the Social Security number
     * to our object. Unlike age, we do need to serialize this information. However, we don't want to store the Social
     * Security (SSN) number in plain text, so we need to write some custom code like the following methods:
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        System.out.println("writeObject()");
        ObjectOutputStream.PutField fields = s.putFields();
        fields.put("id", id);
        fields.put("name", name);
        fields.put("ssn", this.encrypt(ssn));
        s.writeFields();
    }
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        System.out.println("readObject()");
        ObjectInputStream.GetField fields = s.readFields();
        this.id = fields.get("id", -1);
        this.name = (String) fields.get("name", "");
        this.ssn = this.decrypt((String) fields.get("ssn", ""));
    }
    /**
     * When someone reads the data from the disk, it deserializes it into a new object, not the one in memory pool. This
     * could result in two users holding different versions of the Employee in memory. Enter the "readResolve()" method.
     * This method is run after the "readObject()" method and is capable of replacing the reference of the object
     * returned by deserialization.
     */
    public synchronized Object readResolve() throws ObjectStreamException {
        System.out.println("readResolve()");
        var existingEmployee = pool.get(id);
        if(existingEmployee == null) {                  // New Employee, not in memory.
            pool.put(id, this);
            return this;
        } else {                                        // Existing Employee already in memory.
            existingEmployee.id = this.id;
            existingEmployee.name = this.name;
            existingEmployee.ssn = this.ssn;
            return existingEmployee;
        }
    }
    /**
     * Now, what if we want to write an Employee record to disk, but we don't completely trust the instance we are
     * holding? For example, we want to always write the version of the object in the pool rather than the "this"
     * instance. By construction, there should be only one version of this object in memory, but for this example let's
     * pretend we're not 100 percent confident of that.
     *
     * This implementation checks whether the object is found in the pool. If it is found in the pool, that version is
     * sent for serialization; otherwise, the current instance is used.
     *
     * NOTE: We could also update this example to add it to the pool if it is somehow is missing.
     */
    public Object writeReplace() throws ObjectStreamException {
        System.out.println("writeReplace()");
        var e = pool.get(id);
        return e != null ? e : this;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getSsn() { return ssn; }
    public int getId() { return id; }
    public void setAddress(String address) { this.address = address; }

    public static Employee getEmployee(int id) {
        return pool.get(id);
    }
    public static void addEmployee(Employee employee) {
        pool.put(employee.id, employee);
    }
    public static void addEmployees(List<Employee> employees) {
        for (Employee employee : employees)
            pool.put(employee.id, employee);
    }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + '\'' + ", address='" + address + '\'' + ", age=" + age
                + ", ssn='" + ssn + '\'' + '}';
    }
}
/**
 * Class used to solve the StreamCorruptedException: invalid type code: AC, when we tried to deserialize an object in a
 * second writing on a file.
 */
class MyObjectOutputStream extends ObjectOutputStream {
    protected MyObjectOutputStream() throws IOException, SecurityException {
    }
    public MyObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }
    @Override
    public void writeStreamHeader() throws IOException {
        // Nothing to implement
    }
}

/**
 * Methods for Serialization and Deserialization:
 *
 * *************|*******************|***********************|*****************************************************|
 * Return Type  | Method            | Parameters            | Description                                         |
 * *************|*******************|***********************|*****************************************************|
 * Object       | writeReplace()    | None                  | Allows replacement of object before serialization.  |
 * -------------|-------------------|-----------------------|-----------------------------------------------------|
 * void         | writeObject()     | ObjectInputStream     | Serializes optionally using PutField.               |
 * -------------|-------------------|-----------------------|-----------------------------------------------------|
 * void         | readObject()      | ObjectOutputStream    | Deserializes optionally using GetField.             |
 * -------------|-------------------|-----------------------|-----------------------------------------------------|
 * Object       | readResolve()     | None                  | Allows replacement of object after deserialization. |
 * -------------|-------------------|-----------------------|-----------------------------------------------------|
 *
 * Remember that all four of these methods are optional and must be declared in the Serializable object to be used.
 * IMPORTANT: "writeReplace()" happens before "writeObject()", while "readResolve()" happens after "readObject()".
 *
 */
public class SerializingObjects {

    private static final String SERIALIZATION_FILE = "misc/review_chapter_22/serialization_process.txt";

    static {
        if (Files.exists(Path.of(SERIALIZATION_FILE))) {
            try {
                Files.delete(Path.of(SERIALIZATION_FILE));
            } catch (IOException e) {
                System.out.println("Error trying to delete file: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        initialSerializationProcess();
        prePostSerializationProcess();
    }

    private static void initialSerializationProcess() {
        try {
            Files.createFile(Path.of(SERIALIZATION_FILE));
            List<Employee> list = new ArrayList<>();
            list.add(new Employee(1, "Sheldon", 31, "123", "4600 Rodeo Ln"));
            list.add(new Employee(2, "Leonard", 32, "123abc", "4147 S Van Ness Ave"));
            list.add(new Employee(3, "Howard", 30, "123xyz", "1349 W 40th Pl"));
            Employee.addEmployees(list);
            serializingData(list);
            deserializingData();
        } catch (IOException e) {
            System.out.println("Error trying to create file for serializable objects: " + e.getMessage());
        }
    }

    private static void serializingData(List<Employee> list) {
        System.out.println("serializingData(): " + list);
        try {
            if (Files.size(Path.of(SERIALIZATION_FILE)) == 0L) {
                try (var out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(SERIALIZATION_FILE,
                        true)))) {
                    writeObjectToFile(list, out);
                }
            } else {
                try (var out = new MyObjectOutputStream(new BufferedOutputStream(new FileOutputStream(SERIALIZATION_FILE,
                        true)))) {
                    writeObjectToFile(list, out);
                }
            }
        } catch (IOException e) {
            System.out.println("Error trying to serialize objects: " + e.getMessage());
        }

    }
    private static void writeObjectToFile(List<Employee> list, ObjectOutputStream out) throws IOException {
        for (Employee employee : list) {
            out.writeObject(employee);
        }
    }

    private static void deserializingData() {
        System.out.println("deserializingData()");
        var employees = new ArrayList<Employee>();
        try (var in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(SERIALIZATION_FILE)))) {
            boolean continuingReading = true;
            while (continuingReading) {
                try {
                    var object = in.readObject();
                    if (object instanceof Employee)
                        employees.add((Employee) object);
                } catch (ClassNotFoundException e) {
                    System.out.println("ClassNotFoundException: " + e.getMessage());
                } catch (EOFException e) {
                    System.out.println("EOFException: Reach the end of the file of serialized objects.");
                    continuingReading = false;
                }
            }
            // IMPORTANT: EXCEPTION USED WHEN FILE END REACHED.
        } catch (IOException e) {
            System.out.println("Exception getting file of serialized objects: " + e.getMessage());
        }
        System.out.println("Deserialized employees = " + employees);
    }

    /**
     * Suppose our zoo employee application is having a problem with duplicate records being created for each employee.
     * They decide that they want to maintain a list of all employees in memory and only create users as needed.
     */
    private static void prePostSerializationProcess() {
        System.out.println("*** PRE/POST Serialization Process ***");
        List<Employee> list = new ArrayList<>();
        list.add(getOrCreateEmployee(4, "Penny", 20, "abc000", "875 Rucker Rd"));
        serializingData(list);
        deserializingData();
    }
    /**
     * This method creates a new Employee if one does not exist. Otherwise, it returns the one stored in the memory pool.
     */
    public static synchronized Employee getOrCreateEmployee(Integer id, String name, int age, String ssn, String address) {
        System.out.println("getEmployee(): " + id);
        if (Employee.getEmployee(id) == null) {
            var newEmployee = new Employee(id, name, age, ssn, address);
            Employee.addEmployee(newEmployee);
        }
        return Employee.getEmployee(id);
    }
}