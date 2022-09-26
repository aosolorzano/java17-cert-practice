module com.hiperium.java.cert.prep.chapter.eleven.api.dao { // module name should avoid terminal digits
    requires com.hiperium.java.cert.prep.chapter.eleven.api;
    exports com.hiperium.java.cert.prep.chapter._11.api.dao to com.hiperium.java.cert.prep.chapter.eleven.api.impl;
}