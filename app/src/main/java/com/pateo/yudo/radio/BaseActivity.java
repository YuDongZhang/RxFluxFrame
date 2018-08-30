package com.pateo.yudo.radio;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pateo.yudo.radio.dispatcher.RxFlux;
import com.pateo.yudo.radio.dispatcher.RxViewDispatch;
import com.pateo.yudo.radio.dispatcher.action.RxAction;
import com.pateo.yudo.radio.inject.components.ActivityComponent;
import com.pateo.yudo.radio.inject.components.ApplicationComponent;
import com.pateo.yudo.radio.inject.components.DaggerActivityComponent;
import com.pateo.yudo.radio.inject.modules.ActivityModule;
import com.pateo.yudo.radio.store.actions.ActionCreator;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 *
 */

public abstract class BaseActivity extends RxAppCompatActivity implements RxViewDispatch {
    @Inject  //把这个对象注入到这里,对象的创建在 module 中的, 在 ApplicationModule 中.
    protected ActionCreator mRxActionCreator;

    protected final String TAG = this.getClass().getSimpleName();
    protected Context mContext;
    private ActivityComponent mActivityComponent;

    @Inject
    protected RxFlux rxFlux;

    public ActivityComponent activityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(RadioApplications.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }

    public ApplicationComponent applicationComponent() {
        return RadioApplications.get(this).getComponent();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        injectComponent();
        afterCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    /**
     * 1.在ActivityComponent 类注册
     * 2.添加该行代码：
     * activityComponent().inject(this);
     */
    public abstract void injectComponent();
    protected abstract void afterCreate(Bundle savedInstanceState);

    @Override
    protected void onStart() {
        super.onStart();
        rxFlux.getDispatcher().subscribeRxView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        rxFlux.getDispatcher().unsubscribeRxView(this);
    }

    protected boolean isContainRxView(){
        return rxFlux.getDispatcher().isSubscribeRxView(this);
    }
}
