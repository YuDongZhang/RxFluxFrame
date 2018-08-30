package com.pateo.yudo.radio.store.actions;

import com.pateo.yudo.radio.config.ActionKeys;
import com.pateo.yudo.radio.dispatcher.Dispatcher;
import com.pateo.yudo.radio.dispatcher.action.RxAction;
import com.pateo.yudo.radio.dispatcher.action.RxActionCreator;
import com.pateo.yudo.radio.dispatcher.store.SubscriptionManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by pateo on 18-5-11.
 */

public class BaseRxActionCreator extends RxActionCreator {
    /**
     * 构造方法,传入dispatcher和订阅管理器
     *
     * @param dispatcher
     * @param manager
     */
    public BaseRxActionCreator(Dispatcher dispatcher, SubscriptionManager manager) {
        super(dispatcher, manager);
    }

    /**
     * 传递错误,取消引导框
     *
     * @param action
     * @return
     */
    @NonNull
    private Consumer<Throwable> getThrowableAction(final RxAction action) {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                postError(action, throwable);
            }
        };

    }

    protected <T> void postSyncAction(RxAction action, Observable<T> httpObservable) {
        if (hasRxAction(action)) return;
        addRxAction(action, getSubscribe(action, httpObservable));
    }

    /**
     * @param action
     * @return
     */
    private <T> Disposable getSubscribe(RxAction action, Observable<T> syncObservable) {
        return syncObservable// 1:指定IO线程
                .subscribeOn(Schedulers.io())// 1:指定IO线程
                .observeOn(AndroidSchedulers.mainThread())// 2:指定主线程
                .subscribe(// 2:指定主线程
                        getHttpResponseAction1(action),
                        getThrowableAction(action)
                );
    }

    /**
     * 传递数据
     *
     * @param action
     * @return
     */
    @NonNull
    private <T> Consumer<T> getHttpResponseAction1(final RxAction action) {
        return new Consumer<T>() {
            @Override
            public void accept(T t) throws Exception {
                action.getData().put(ActionKeys.RESPONSE, t);
                postRxAction(action);
            }
        };
    }

}
