package com.pateo.yudo.radio.inject;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by pateo on 18-5-11.
 */

@Scope
@Retention(RUNTIME)
public @interface PerFragment {
}