package com.hiperium.java.cert.prep.chapter._17;

import com.hiperium.java.cert.prep.chapter._11.api.entity.Actor;
import com.hiperium.java.cert.prep.chapter._11.api.service.Chapter11ServiceLocator;

import java.util.List;

public class APIModuleClient {

    public static void main(String[] args) {
        System.out.println("Getting all actors of the Big Bang Theory...");
        List<Actor> actors = Chapter11ServiceLocator.getApiImpl().findAll();
        System.out.println(actors);
    }
}
