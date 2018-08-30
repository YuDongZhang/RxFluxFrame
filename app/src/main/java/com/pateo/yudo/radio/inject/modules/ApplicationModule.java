package com.pateo.yudo.radio.inject.modules;

import android.app.Application;
import android.content.Context;

import com.pateo.yudo.radio.dispatcher.Dispatcher;
import com.pateo.yudo.radio.dispatcher.RxBus;
import com.pateo.yudo.radio.dispatcher.RxFlux;
import com.pateo.yudo.radio.inject.ContextLife;
import com.pateo.yudo.radio.store.BaseRxStore;
import com.pateo.yudo.radio.store.actions.ActionCreator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 组件的依赖, application 中的东西 , activity要直接注入, 现在下面的对象由 applicationModule
 * 例如，现在由 AppModule 提供Context对象， ActivityModule 自己无需提供Context对象，而只需要依赖于 AppModule，
 * 然后获取Context 对象即可。 本类中为 mApplication
 *
 * 对象实例化在: Rxflux,Dispather,Baserxstore,ActionCreator  你还得查看他们父类的创建
 */
@Module
public class ApplicationModule {


    Application mApplication;

    /**
     * 带有参数的 module 构造方法,必须显式的调用生成实例对象
     *
     * @param application
     */
    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides // 添加@Singleton标明该方法产生只产生一个实例
    @Singleton
    @ContextLife("Application") //并没有两个相同的方法 , 不需要进行标示
    public Context provideContext() {
        return mApplication.getApplicationContext();
    }


    @Provides
    @Singleton
    RxFlux rxflux() {
        return new RxFlux(mApplication);
    }

    @Provides
    @Singleton
    Dispatcher dispatcher() {
        return new Dispatcher(RxBus.getInstance());
    }

    @Provides
    @Singleton
    public BaseRxStore provideBaseStore(RxFlux rxFlux) {
        return new BaseRxStore(rxFlux.getDispatcher());
    }

    @Provides
    @Singleton
    public ActionCreator provideActionCreator(RxFlux rxFlux) {
        if (rxFlux == null) rxFlux = rxflux();
        return new ActionCreator(rxFlux.getDispatcher(), rxFlux.getDisposableManager());
    }
}
