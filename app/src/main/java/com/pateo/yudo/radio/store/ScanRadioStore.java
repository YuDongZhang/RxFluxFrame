package com.pateo.yudo.radio.store;

import com.pateo.yudo.radio.dispatcher.Dispatcher;
import com.pateo.yudo.radio.dispatcher.action.RxAction;
import com.pateo.yudo.radio.dispatcher.store.RxStoreChange;
import com.pateo.yudo.radio.util.Log;

/**
 * Created by pateo on 18-5-10.
 */

public class ScanRadioStore extends BaseRxStore {
    /**
     * 构造方法,传入dispatcher
     *
     * @param dispatcher
     */

    public ScanRadioStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void onRxAction(RxAction action) {
        Log.i(Log.TAG, "baseRx Store");

        postChange(new RxStoreChange(getClass().getSimpleName(), action));// 只发送自己处理的action

    }
}
