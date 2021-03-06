package com.pateo.yudo.radio.dispatcher.store;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;

import com.pateo.yudo.radio.dispatcher.action.RxAction;

import io.reactivex.disposables.Disposable;


/**
 * Utility class to handle subscriptions
 */
public final class SubscriptionManager {


    private static SubscriptionManager sInstance;
    /**
     * 管理订阅的ArrayMap
     */
    private ArrayMap<String, Pair<Integer, Disposable>> mMap;

    private SubscriptionManager() {
        mMap = new ArrayMap<>();
    }

    public static synchronized SubscriptionManager getInstance() {
        if (sInstance == null) sInstance = new SubscriptionManager();
        return sInstance;
    }

    /**
     * Given an action and a disposable, add the new disposable and unSubscribe if there
     * was an existing one.
     * 添加一个action和disposable,如果已经有了一个对应action 的订阅,则取消订阅
     */
    public void add(RxAction action, Disposable disposable) {
        Pair<Integer, Disposable> old = mMap.put(action.getType(), getPair(action, disposable));
        if (old != null && !old.second.isDisposed()) old.second.dispose();
    }

    /**
     * Remove an rxAction and unSubscribe from it
     * 从管理器中取消订阅
     */
    public void remove(RxAction action) {
        Pair<Integer, Disposable> old = mMap.remove(action.getType());
        if (old != null && !old.second.isDisposed()) old.second.dispose();
    }

    /**
     * Checks if the action (with the same params) is already running a disposable
     * 检查action是否已经运行一个disposable
     *
     * @return true if the exact action is inside the map and running
     */
    public boolean contains(RxAction action) {
        Pair<Integer, Disposable> old = mMap.get(action.getType());
        return old != null && old.first == action.hashCode() && !old.second.isDisposed();
    }

    /**
     * Clear all the disposables
     * 清除所有的disposables
     */
    public synchronized void clear() {
        if (mMap.isEmpty()) return;
        for (Pair<Integer, Disposable> pair : mMap.values())
            if (!pair.second.isDisposed())
                pair.second.dispose();
    }

    /**
     * 创建一个新的pair
     *
     * @param action     转变成hashcode
     * @param disposable
     * @return
     */
    private Pair<Integer, Disposable> getPair(RxAction action, Disposable disposable) {
        return new Pair<>(action.hashCode(), disposable);
    }
}
