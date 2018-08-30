package com.pateo.yudo.radio.inject.components;

import android.app.Application;
import android.content.Context;

import com.pateo.yudo.radio.RadioApplications;
import com.pateo.yudo.radio.dispatcher.Dispatcher;
import com.pateo.yudo.radio.dispatcher.RxFlux;
import com.pateo.yudo.radio.inject.ContextLife;
import com.pateo.yudo.radio.inject.modules.ApplicationModule;
import com.pateo.yudo.radio.store.BaseRxStore;
import com.pateo.yudo.radio.store.actions.ActionCreator;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 里面的一些get 方法都是向下层提供,
 * 没有直接和activity发生关系 ,没有inject()activity 这样的方法. 在radioapplication 中来实践化
 * 这里面来进行 inject() RadioApplication
 */
@Singleton
@Component(modules = ApplicationModule.class)  //这个地方和相应的 modules 来进行连接
public interface ApplicationComponent {

    @ContextLife("Application") //提供给下层的时候进行标示  好像没有必要  像这些没有return 怎么处理
    Context getContext();

    Application application();

    RxFlux rxflux();

    Dispatcher dispatcher();

    BaseRxStore getbaseRxStore();

    ActionCreator getActionCreator();

    void inject(RadioApplications radioApplications);

    void inject(ActionCreator actionCreator);

    void inject(BaseRxStore baseRxStore);
}
