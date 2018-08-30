package com.pateo.yudo.radio.inject.modules;

import android.app.Activity;
import android.content.Context;

import com.pateo.yudo.radio.dispatcher.RxFlux;
import com.pateo.yudo.radio.inject.ContextLife;
import com.pateo.yudo.radio.inject.PerActivity;
import com.pateo.yudo.radio.store.ScanRadioStore;

import java.lang.ref.WeakReference;

import dagger.Module;
import dagger.Provides;

/**
 * 1. ActivityModule 也需要创建Person时的Context对象，但是本类中却没有 providesContext() 的方法，因为它通过
 * ActivityComponent依赖于 AppComponent，所以可以通过 AppComponent中的 providesContext() 方法获取到Context
 * 对象。2. AppComponent中必须提供 Context getContext(); 这样返回值是 Context 对象的方法接口，
 * 否则ActivityModule中无法获取。
 */

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides //提供对象
    @ContextLife("Activity") //对应在他相应的 Component 中,
    public Context provideContext() {
        //使用弱引用,消除内存泄漏
        return new WeakReference<>(mActivity).get();
    }

    @Provides
    @PerActivity
    public Activity provideActivity() {
        //使用弱引用,消除内存泄漏
        return new WeakReference<>(mActivity).get();
    }

    @Provides
    @PerActivity
    public FragmentModule provideFragmentModule() {
        return new FragmentModule();
    }

    @Provides
    @PerActivity
    public ScanRadioStore provideScanRadioStore(RxFlux rxFlux) {
        return new ScanRadioStore(rxFlux.getDispatcher());
    }


}