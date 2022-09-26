package com.hiperium.java.cert.prep.chapter._11.api.dao;

import com.hiperium.java.cert.prep.chapter._11.api.Chapter11API;
import com.hiperium.java.cert.prep.chapter._11.api.entity.Actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Chapter11APIDAO implements Chapter11API {

    private static final List<Actor> USERS = new ArrayList<>();
    private static Chapter11APIDAO instance;

    private Chapter11APIDAO() {
        USERS.add(new Actor(1, "Johnny Galecki", "Leonard Hofstadter",      "Experimental Physicist"));
        USERS.add(new Actor(2, "Jim Parsons",    "Sheldon Cooper",          "Theoretical Physicist"));
        USERS.add(new Actor(3, "Kaley Cuoco",    "Penny",                   "Pharmaceutical Sales Representative"));
        USERS.add(new Actor(4, "Simon Helberg",  "Howard Wolowitz",         "Aerospace Engineer"));
        USERS.add(new Actor(5, "Kunal Nayyar",   "Rajesh Koothrappali",     "Astrophysicist"));
        USERS.add(new Actor(6, "Melissa Rauch",  "Bernadette Rostenkowski", "Ph.D. in Microbiology"));
        USERS.add(new Actor(7, "Mayim Bialik",   "Amy Farrah Fowler",       "Ph.D. in Neurobiology"));
    }

    public static Chapter11APIDAO getInstance() {
        if (Objects.isNull(instance)) {
            instance = new Chapter11APIDAO();
        }
        return instance;
    }

    public List<Actor> findAll() {
        return USERS;
    }

    @Override
    public Actor findByPersonage(String personage) {
        return USERS.stream().filter(s -> s.personage.startsWith(personage)).findFirst().orElse(null);
    }
}
