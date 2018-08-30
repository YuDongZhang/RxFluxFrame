package com.pateo.yudo.radio;

import android.app.Application;
import android.content.Context;

import com.pateo.yudo.radio.inject.components.ApplicationComponent;
import com.pateo.yudo.radio.inject.components.DaggerApplicationComponent;
import com.pateo.yudo.radio.inject.modules.ApplicationModule;
import com.pateo.yudo.radio.store.BaseRxStore;
import com.pateo.yudo.radio.util.AppUtils;
import com.pateo.yudo.radio.util.Log;

import javax.inject.Inject;
/**
 * BaseRxStore mBaseStore 在 applicationModel中来进行实例化,
 */

public class RadioApplications extends Application {
    protected final String TAG = this.getClass().getSimpleName();

    ApplicationComponent mApplicationComponent;

    @Inject
    BaseRxStore mBaseStore;

    public static RadioApplications get(Context context) {
        return (RadioApplications) context.getApplicationContext();
    }

    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "RadioApplications");
        AppUtils.setApplication(this);
        //在这个地方 来进行实例化, 并且把 applicationModule(this) 来进行实例化,有个inject方法加入this
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        AppUtils.setApplicationComponent(mApplicationComponent);
        mApplicationComponent.inject(this);
        mBaseStore.register();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
