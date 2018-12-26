package jx.com.day1_fanshe.annotation;


import android.support.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//在哪里都可以用
//@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectAnnotation {
    @IdRes int value();
}
