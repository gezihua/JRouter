package com.superx.j.regedit.annation;

/**
 * PKG com.superx.j.regedit.annation
 * Created by suj on 2019-08-24.
 * DES:
 * QQ:1553202383
 */
@java.lang.annotation.Documented
@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.LOCAL_VARIABLE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)

public @interface JRegeditAnnation {
    boolean lazyInit() default true;

    Class[] interfaces();
}


// IntelliJ API Decompiler stub source generated from a class file
// Implementation of methods is not available

