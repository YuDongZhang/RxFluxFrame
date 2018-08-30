package com.pateo.yudo.radio.inject.components;

import android.content.Context;

import com.pateo.yudo.radio.inject.ContextLife;
import com.pateo.yudo.radio.inject.PerActivity;
import com.pateo.yudo.radio.inject.modules.ActivityModule;
import com.pateo.yudo.radio.ui.DemoActivity;

import dagger.Component;

/**
 *@Component(dependencies = {AppComponent.class}, modules = {ActivityModule.class})
 *
 *
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    /**
     * 通过自定义的@ContextLife区分返回类型相同的@Provides 方法
     *
     * 这个都是获得context的 , 区分返回的类型 ,你要注意@Provides 这个方法返回的类型
     * @return
     */
    @ContextLife("Activity") //有返回类型相同的
    Context getActivityContext();

    /**
     * 从对应的ActivityModule中找不到,从dependencies的ApplicationComponent中找得到
     *
     * @return
     */
    @ContextLife("Application") //有返回的类型相同的
    Context getApplicationContext();

    /**
     * 需要在父Component(ActivityComponent)添加返回子Component(FragmentComponent)的方法
     *
     * @return
     */
    FragmentComponent getFragmentComponent();

    void inject(DemoActivity mainActivity);
}
