package com.hiperium.java.cert.prep.chapter._13;

import java.lang.annotation.*;

/**
 * With the @Retention annotation, the compiler can discard certain types of information when converting your source
 * code into a .class file. With generics, this is known as type erasure. In a similar way, annotations may be discarded
 * by the compiler or at runtime. We can specify how they are handled using the @Retention annotation. This annotation
 * takes a value() of the enum RetentionPolicy.
 */
@Retention(RetentionPolicy.CLASS)   @interface Flier {}
@Retention(RetentionPolicy.RUNTIME) @interface Swimmer {}
@Retention(RetentionPolicy.SOURCE)  @interface Running {}

/**
 * When @Vertebrate annotation is applied to a class, subclasses of it will inherit the annotation information found in
 * the parent class.
 */
@Inherited
@interface Vertebrate {}

@Vertebrate
class Dolphin {}

/**
 * If @Documented annotation is present, then the generated Javadoc will include annotation information defined on Java
 * types. Because it is a marker annotation, it doesn't take any values.
 */
@Documented
@interface Hunter {}

/**
 * In class section, we will cover built‐in annotations applied to other annotations. Since these annotations are built
 * into Java, they primarily impact the compiler.
 *
 * The @Hunter annotation would be published with the AnnotationSpecificAnnotations Javadoc information because it's
 * marked with the @Documented annotation.
 *
 * The @Vertebrate annotation will be applied to both Dolphin and AnnotationSpecificAnnotations objects. Without
 * the @Inherited annotation, @Vertebrate would apply only to AnnotationSpecificAnnotations instances.
 *
 */
@Hunter
public class AnnotationSpecificAnnotations extends Dolphin {
    /**
     * The TYPE_USE parameter can be used anywhere there is a Java type. By including it in @Target, it actually includes
     * nearly in classes, interfaces, constructors, parameters, and more. There are a few exceptions; for example, it can
     * be used only on a method that returns a value. Methods that return void would still need the METHOD value defined
     * in the annotation.
     */
    @Target(ElementType.TYPE_USE)
    @interface Technical {}

    private static class NetworkRepair {
        private class OutSrc extends @Technical NetworkRepair {}
        private static class OutSourcing extends @Technical NetworkRepair {}
        public static void repair() {
            var repairSubclass = new @Technical NetworkRepair() {};
            var outSrc         = new @Technical NetworkRepair().new @Technical OutSrc();
            var outSourcing    = new NetworkRepair.OutSourcing();
            // var outSourcing = new @Technical NetworkRepair.OutSourcing();    ERROR: Static member qualifying type
            //                                                                  may not be annotated.
            @Technical int remaining    = (@Technical int) 10.0;
            // @Technical var remaining = (@Technical int) 10.0;                ERROR: 'var' type may not be annotated.
        }
    }

    /**
     * The @Repeatable annotation is used when you want to specify an annotation more than once on a type. Generally,
     * when you want to apply with different values.
     *
     * NOTE: The @Target annotation cannot be used in conjunction with @Repeatable.
     */
    @Target(ElementType.FIELD)
    @interface Risks {
        Risk[] value();
    }
    @Repeatable(Risks.class)
    @interface Risk {
        String danger();
        int level() default 1;
    }
    @Risk(danger = "Silly")
    @Risk(danger = "Aggressive", level = 5)
    @Risk(danger = "Violent",    level = 10)
    private NetworkRepair networkRepair;
}
