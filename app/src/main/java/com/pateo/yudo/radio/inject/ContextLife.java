package com.pateo.yudo.radio.inject;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 *  @Qualifier  //自定义的标签
 *  如果Person中有两个构造方法，那么在依赖注入的时候，它怎么知道我该调用哪个构造方法呢？
 *  可以使用 @Named 使用的字符串容易出错,
 *  在 ActivityModule 中用到的 主要在与区分 new WeakReference<>(mActivity).get();有两个相同的方法
 *
 */

@Qualifier  //自定义的标签
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ContextLife {
    String value() default "Application";
}
