package com.hiperium.java.cert.prep.chapter._11_Modules.api.impl;

import com.hiperium.java.cert.prep.chapter._11_Modules.api.Chapter11API;
import com.hiperium.java.cert.prep.chapter._11_Modules.api.dao.Chapter11APIDAO;
import com.hiperium.java.cert.prep.chapter._11_Modules.api.entity.Actor;

import java.util.List;

public class Chapter11APIImpl implements Chapter11API {

    private final Chapter11APIDAO dao = Chapter11APIDAO.getInstance();

    @Override
    public List<Actor> findAll() {
        return dao.findAll();
    }

    @Override
    public Actor findByPersonage(String personage) {
        return dao.findByPersonage(personage);
    }
}
