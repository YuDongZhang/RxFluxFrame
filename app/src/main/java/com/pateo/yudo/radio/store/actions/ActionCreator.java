package com.pateo.yudo.radio.store.actions;

import com.pateo.yudo.radio.dispatcher.Dispatcher;
import com.pateo.yudo.radio.dispatcher.store.SubscriptionManager;
import com.pateo.yudo.radio.util.AppUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by pateo on 18-5-11.
 */

public class ActionCreator extends BaseRxActionCreator implements Actions {
    /**
     * 构造方法,传入dispatcher和订阅管理器
     *
     * @param dispatcher
     * @param manager
     */
    @Inject
    public ActionCreator(Dispatcher dispatcher, SubscriptionManager manager) {
        super(dispatcher, manager);
        AppUtils.getApplicationComponent().inject(this);
    }

    @Override
    public void postBaseAction(@NonNull String actionId, @NonNull Object... data) {
        postRxAction(newRxAction(actionId, data));
    }

    @Override
    public void postSycnAction(final String actionId, Object... data) {
        Observable.just("scanUSB").map(new Function<String, Object>() {
            @Override
            public Object apply(String s) throws Exception {
                //
                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                postRxAction(newRxAction(actionId, new Object[]{}));

            }
        });
    }


}
