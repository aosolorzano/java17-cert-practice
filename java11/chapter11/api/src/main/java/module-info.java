module com.hiperium.java.cert.prep.chapter.eleven.api { // module name should avoid terminal digits.
    exports com.hiperium.java.cert.prep.chapter._11.api;
    exports com.hiperium.java.cert.prep.chapter._11.api.entity;
    exports com.hiperium.java.cert.prep.chapter._11.api.service;
    uses com.hiperium.java.cert.prep.chapter._11.api.Chapter11API;  // Must be required by Service Loader.
}