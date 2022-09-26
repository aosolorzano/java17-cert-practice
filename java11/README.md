### java11-cert-practice

This project is the implementation of some exercises or existing examples in the book "OCP Complete Study Guide" and the "OCP Practice Test" by Jeanne Boyarsky and Scott Selikoff for the OCP Certification exam.
This is an excellent material to prepare for the Java 11 certification, and I recommend it to you.

You can find more information about these books in the following pages:

* The [Complete Study Guide](https://www.selikoff.net/ocp11-complete/).
* The [Practice Tests](https://www.selikoff.net/ocp11-pt/).
* To buy the book: [Amazon](https://www.amazon.com/Oracle-Certified-Professional-Developer-Complete-ebook/dp/B08DF4R2V9).

In the following sections, I describe some commands or instructions that I share with you, in my journey to take the OCP Java 11 Developer exam.

### Modular Applications
You may have to create the "mods" directory before running depending on your OS:
```
mkdir mods
```

#### Compiling, packaging and running a Modular Program
If you are using Maven, you need to clean the project first:
```
mvn clean
```
I use the target/classes en each module to store compiled classes. This because we need to follow the standards for tools like Maven or Gradle.
Compile in the same order that I show below:

#### Compiling API Module:
```
javac -d chapter11/api/target/classes \
    chapter11/api/src/main/java/com/hiperium/java/cert/prep/chapter/_11/api/*.java \
    chapter11/api/src/main/java/com/hiperium/java/cert/prep/chapter/_11/api/**/*.java \
    chapter11/api/src/main/java/module-info.java
```

#### Packaging API Module
```
jar -cvf mods/java11-cert-practice-chapter11-api.jar -C chapter11/api/target/classes .
```

#### Compiling and packaging the rest of the modules
We need to apply the same set of commands for the rest of the modules. I also include the --module-path (or -p) parameter to reference the compiled and packaged ones.

#### API-DAO Module:
```
javac -p mods -d chapter11/api-dao/target/classes \
    chapter11/api-dao/src/main/java/com/hiperium/java/cert/prep/chapter/_11/api/dao/*.java \
    chapter11/api-dao/src/main/java/module-info.java
```
```
jar -cvf mods/java11-cert-practice-chapter11-api-dao.jar -C chapter11/api-dao/target/classes .
```

#### API-IMPL Module:
```
javac -p mods -d chapter11/api-impl/target/classes \
    chapter11/api-impl/src/main/java/com/hiperium/java/cert/prep/chapter/_11/api/impl/*.java \
    chapter11/api-impl/src/main/java/module-info.java
```
```
jar -cvf mods/java11-cert-practice-chapter11-api-impl.jar -C chapter11/api-impl/target/classes .
```

#### CHAPTERS Module:
```
javac -p mods -d chapters/target/classes \
    chapters/src/main/java/com/hiperium/java/cert/prep/chapter/_11/*.java \
    chapters/src/main/java/com/hiperium/java/cert/prep/chapter/_17/*.java \
    chapters/src/main/java/module-info.java
```
We can set the JAR main class when generating the module JAR file. We can do so by providing an "e" argument:
```
jar -cvfe mods/java11-cert-practice-chapters.jar com.hiperium.java.cert.prep.chapter._17.APIModuleClient -C chapters/target/classes .
```

#### Running program modularity
Now, we can run the test class, using the generated CHAPTERS JAR:
```
java --module-path mods --module com.hiperium.java.cert.prep.chapters/com.hiperium.java.cert.prep.chapter._11.JavaModuleClient
```
```
java -p mods -m com.hiperium.java.cert.prep.chapters/com.hiperium.java.cert.prep.chapter._17.APIModuleClient
```
Or simply using the jar directive:
```
java -p mods -jar mods/java11-cert-practice-chapters.jar
```

### Discovering Modules
We need to know the syntax of the following commands and what they do.

#### JAVA command
The java command has three module-related options. I show bellow the use of each one of them.

1. Describing a module:
```
java -p mods --describe-module com.hiperium.java.cert.prep.chapter.eleven.api.impl
```
Or
```
java -p mods -d com.hiperium.java.cert.prep.chapter.eleven.api.impl
```

2. Listing available modules:
The simplest form, lists the modules that are part of the JDK:
```
java --list-modules
```
We can also use it with our module directory:
```
java -p mods --list-modules
```

3. Showing Module resolution:
We can think of it as a way of debugging modules. It prints out a lot of info when the program starts up, and then, it runs the program.

```
java --show-module-resolution -p mods \
    -m com.hiperium.java.cert.prep.chapters/com.hiperium.java.cert.prep.chapter._17.APIModuleClient
```

#### JAR command
```
jar --file mods/java11-cert-practice-chapter11-api-impl.jar --describe-module
```
Or
```
jar -f mods/java11-cert-practice-chapter11-api-impl.jar -d
```

#### JDEPS command
Unlike describing a module, it looks at the code in addition to the module-info file. This tell us what dependencies are actually used rather than simply declared.
```
jdeps -summary mods/java11-cert-practice-chapter11-api.jar
```
Or
```
jdeps -s mods/java11-cert-practice-chapter11-api.jar
```
Note: the summary option only have one dash.

Now, I pick a module that depends on another. For this, we need to specify the module path, so jdeps knows where to find info about the dependent module.
```
jdeps -summary --module-path mods mods/java11-cert-practice-chapter11-api-impl.jar
```
Or
```
jdeps -s --module-path mods mods/java11-cert-practice-chapter11-api-impl.jar
```
Note: There is not a short form of --module-path in the jdeps command.

#### JDEPS for Non-Modular Application
I have created a JAR inside the "ext" directory that is not a modular application:
```
cd ext
javac zoo/dinos/*.java
jar -cvf zoo.dino.jar dino
```
Note: If we want to add an entry main class for a Jar, we can use the "-e" parameter like this:
```
javac schedule/test/*.java
jar -cvfe schedule-test.jar schedule.test.Schedule schedule
```

Now, we can view what packages it use and what modules it corresponds to:
```
jdeps zoo.dino.jar
```
We can also see the output in a summary mode:
```
jdeps -s zoo.dino.jar
```
Or
```
jdeps -summary mods/zoo.dino.jar
```
Note: For a real project, the dependency list could include dozens or even hundreds of packages. It's useful to see the summary of just the modules.

The jdeps command has an option to provide details about unsupported APIs:
```
jdeps --jdk-internals ext/zoo.dino.jar
```
At the end of the output, the command provides a table suggesting what I should do about those APIs.


### Command-Line Options

#### Options we need to know for the "javac" command:

| Option | Description |
| ------ | ----------- |
| -cp \<classpath><br/> -classpath \<classpath><br/> --class-path \<classpath> | Location of JARs in a non-modular program. |
| -d \<dir>                                                                    | Directory to place generated class files.  |
| -p \<path><br/> --module-path \<path>                                        | Location of JARs in a modular program.     |

#### Options we need to know for the "java" command:

| Option | Description |
| ------ | ----------- |
| -p \<path><br/> --module-path \<path>     | Location of JARs in a modular program.                |
| -m \<name><br/> --module \<name>          | Module name to run.                                   |
| -d <br/> --describe-module                | Describes the details of a module.                    |
| --list-modules                            | Lists observable modules without running a program.   |
| --show-module-resolution                  | Shows modules when running program.                   |

#### Options we need to know for the "jar" command:
| Option | Description |
| ------ | ----------- |
| -c<br/> --create          | Create a new JAR file.                                     |
| -v<br/> --verbose         | Prints details when working with JAR files.                |
| -f<br/> --file            | JAR filename.                                              |
| -C<br/>                   | Directory containing files to be used to create the JAR.   |
| -d<br/> --describe-module | Describes the details of a module.                         |

### Packing a Java Module as a Standalone Application
```
jlink --module-path "mods;$JAVA_HOME/jmods" --add-modules com.hiperium.java.cert.prep.chapters --output chapters-jre
```
