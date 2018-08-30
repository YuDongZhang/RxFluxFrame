package com.pateo.yudo.radio.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.pateo.yudo.radio.BaseFragment;
import com.pateo.yudo.radio.R;
import com.pateo.yudo.radio.config.ActionKeys;
import com.pateo.yudo.radio.dispatcher.store.RxStoreChange;

import butterknife.OnClick;

public class DemoFragment extends BaseFragment {
    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @OnClick(R.id.menu)
    public void menu(){
        String clickInfo="Fragment发送事件:"+System.nanoTime();
      mRxActionCreator.postBaseAction(ActionKeys.DEMO_TEST_EXAMPLE,ActionKeys.KEY,clickInfo);
    }
    @Override
    protected int getLayoutId() {

        return R.layout.fragment_demo;
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {

    }
}
