package com.pateo.yudo.radio.store;

import com.pateo.yudo.radio.dispatcher.Dispatcher;
import com.pateo.yudo.radio.dispatcher.action.RxAction;
import com.pateo.yudo.radio.dispatcher.store.RxStore;
import com.pateo.yudo.radio.dispatcher.store.RxStoreChange;
import com.pateo.yudo.radio.store.actions.ActionCreator;
import com.pateo.yudo.radio.store.actions.Actions;
import com.pateo.yudo.radio.util.AppUtils;

import javax.inject.Inject;

/**
 * Created by pateo on 18-5-11.
 */

public class BaseRxStore extends RxStore {
    @Inject
    protected ActionCreator mActionCreator;

    /**
     * 构造方法,传入dispatcher,
     *
     * @param dispatcher
     */
    public BaseRxStore(Dispatcher dispatcher) {
        super(dispatcher);
        AppUtils.getApplicationComponent().inject(this);
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case Actions.ERROR_RADIOMANAGER_INFO:
                //TODO 考虑如何抛异常给RadioManger
                return;
            default:// 必须有,接收到非自己处理的action返回
                break;
        }
        postChange(new RxStoreChange(getClass().getSimpleName(), action));// 只发送自己处理的action

    }
}
