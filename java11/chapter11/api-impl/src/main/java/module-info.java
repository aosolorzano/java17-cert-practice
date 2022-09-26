module com.hiperium.java.cert.prep.chapter.eleven.api.impl { // module name should avoid terminal digits
    requires com.hiperium.java.cert.prep.chapter.eleven.api;
    requires com.hiperium.java.cert.prep.chapter.eleven.api.dao;
    provides com.hiperium.java.cert.prep.chapter._11.api.Chapter11API with com.hiperium.java.cert.prep.chapter._11.api.impl.Chapter11APIImpl;
}