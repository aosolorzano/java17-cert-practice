package com.hiperium.java.cert.prep.chapter._11.api.entity;

import java.util.Objects;

public class Actor {

    public int id;
    public String name;
    public String personage;
    public String role;

    public Actor() {
        // Nothing to implement
    }

    public Actor(int id, String name, String personage, String role) {
        this.id = id;
        this.name = name;
        this.personage = personage;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonage() {
        return personage;
    }

    public void setPersonage(String personage) {
        this.personage = personage;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return id == actor.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", personage='" + personage + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
