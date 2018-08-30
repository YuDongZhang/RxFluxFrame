package com.pateo.yudo.radio.inject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * 自定义的生命周期的 
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
