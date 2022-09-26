package com.hiperium.java.cert.prep.chapter._11.api.service;

import com.hiperium.java.cert.prep.chapter._11.api.Chapter11API;
import java.util.ServiceLoader;

public final class Chapter11ServiceLocator {

    private static final ServiceLoader<Chapter11API> loader = ServiceLoader.load(Chapter11API.class);
    private static final Chapter11API api;

    static {
        api = loader.findFirst().orElseThrow(() -> new IllegalStateException("API Service Implementation not found."));
    }

    private Chapter11ServiceLocator() {
        // Nothing to implement
    }

    public static Chapter11API getApiImpl() {
        return api;
    }
}
