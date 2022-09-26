package com.hiperium.java.cert.prep.chapter._11.api;

import com.hiperium.java.cert.prep.chapter._11.api.entity.Actor;

import java.util.List;

public interface Chapter11API {
    List<Actor> findAll();
    Actor findByPersonage(String personage);
}
