package com.hiperium.java.cert.prep.chapter._11_Modules;

import com.hiperium.java.cert.prep.chapter._11_Modules.api.entity.Actor;
import com.hiperium.java.cert.prep.chapter._11_Modules.api.service.Chapter11ServiceLocator;

public class JavaModuleClient {

    public static void main(String[] args) {
        System.out.println("Getting Sheldon's data of the Big Bang Theory...");
        Actor actor = Chapter11ServiceLocator.getApiImpl().findByPersonage("Sheldon");
        System.out.println(actor);
    }
}
