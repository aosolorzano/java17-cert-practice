package com.hiperium.java.cert.prep.chapter._13;

import java.lang.annotation.Repeatable;

class Food {}
enum Color { GREY, BROWN }
@interface Snow {
    boolean value();
}
@interface Injured {
    public static final int slippery = 5;
    String veterinarian() default "Unassigned";
    String value()        default "foot";
    int age()             default 1;
    int level()           default (int) 1_000.0;
    int cunning()         default Integer.MAX_VALUE;
    Color color()         default Color.GREY;
    Snow snow()           default @Snow(true);
    // private   int val = 5;                               ERROR: Modifier 'private' not allowed here.
    // protected int val();                                 ERROR: Modifier 'protected' not allowed here.
    // Integer       val() default 1;                       ERROR: Wrapper classes of primitive types are not supported.
    // String[][]    val();                                 ERROR: The type String[] is supported, but String[][] is not.
    // String        val() default null;                    ERROR: Attribute value must be constant.
    // String        val() default new String();            ERROR: Attribute value must be constant
    // String        val() default new String("");          ERROR: Attribute value must be constant
    // int           val() default Integer.valueOf(3);      ERROR: Attribute value must be constant
    // Food          val();                                 ERROR: Invalid type 'Food' for annotation member.
    // void          val();                                 ERROR: Invalid type 'void' for annotation member.
    // List<String>  val();                                 ERROR: Lists are not supported, or any type of Collection.
    // Map<String, String> val();                           ERROR: Maps are not supported.
}
@Repeatable(Forecast.class)
@interface Weather {
    String value();
}
@interface Forecast {
    Weather[] value();
}

@interface Music {
    String[] genres();
}
@interface Rhythm {
    String[] value();
}

/**
 * Class that use custom and provided annotations.
 */
public abstract class CustomAnnotations {
    /**
     * USING THE SHORTHAND AND LONG FORM NOTATION.
     */
    @Injured                 String[] injuries;             // Annotation with no required values.
    @Injured("Legs")         public void fallDown() {}      // Shorthand notation. Valid only if there is a 'value' element.
    @Injured(value = "Legs") public abstract int trip();    // Long form with a value element.

    /**
     * Using chained annotation declaration
     */
    @Injured(snow = @Snow(false))
    private String snow;

    @Forecast({@Weather("Rainy"), @Weather("Cloudy")})
    private String[] forecast;

    /**
     * USING ARRAYS FOR ELEMENT'S VALUES
     */
    @Music(genres = {})                 String alternate;
    @Music(genres = { "Blues","Jazz" }) String favorites;
    // @Music(genres="Blues","Jazz")    String favorite;    DOES NOT COMPILE
    // @Music(genres=null)              String other;       DOES NOT COMPILE

    /**
     * USING ARRAYS FOR A SHORTHAND NOTATION
     */
    @Rhythm(value = {"Pop"})       String favorite;
    @Rhythm(value = "R&D")         String secondFavorite;
    @Rhythm({"Classical","Swing"}) String mostDisliked;
    @Rhythm("Country")             String lastDisliked;
}


