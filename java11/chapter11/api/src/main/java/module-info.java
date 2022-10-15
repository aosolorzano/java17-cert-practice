module com.hiperium.java.cert.prep.chapter.eleven.api { // module name should avoid terminal digits.
    exports com.hiperium.java.cert.prep.chapter._11_Modules.api;
    exports com.hiperium.java.cert.prep.chapter._11_Modules.api.entity;
    exports com.hiperium.java.cert.prep.chapter._11_Modules.api.service;
    uses com.hiperium.java.cert.prep.chapter._11_Modules.api.Chapter11API;  // Must be required by Service Loader.
}
