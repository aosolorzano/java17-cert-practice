#!/bin/bash
rm -f mods/*
mvn clean
javac -d chapter11/api/target/classes \
    chapter11/api/src/main/java/com/hiperium/java/cert/prep/chapter/_11/api/*.java \
    chapter11/api/src/main/java/com/hiperium/java/cert/prep/chapter/_11/api/**/*.java \
    chapter11/api/src/main/java/module-info.java
jar -cvf mods/java11-cert-practice-chapter11-api.jar -C chapter11/api/target/classes .
javac -p mods -d chapter11/api-dao/target/classes \
    chapter11/api-dao/src/main/java/com/hiperium/java/cert/prep/chapter/_11/api/dao/*.java \
    chapter11/api-dao/src/main/java/module-info.java
jar -cvf mods/java11-cert-practice-chapter11-api-dao.jar -C chapter11/api-dao/target/classes .
javac -p mods -d chapter11/api-impl/target/classes \
    chapter11/api-impl/src/main/java/com/hiperium/java/cert/prep/chapter/_11/api/impl/*.java \
    chapter11/api-impl/src/main/java/module-info.java
jar -cvf mods/java11-cert-practice-chapter11-api-impl.jar -C chapter11/api-impl/target/classes .
javac -p mods -d chapters/target/classes \
    chapters/src/main/java/com/hiperium/java/cert/prep/chapter/_11/*.java \
    chapters/src/main/java/com/hiperium/java/cert/prep/chapter/_17/*.java \
    chapters/src/main/java/module-info.java
jar -cvfe mods/java11-cert-practice-chapters.jar com.hiperium.java.cert.prep.chapter._17.APIModuleClient -C chapters/target/classes .
