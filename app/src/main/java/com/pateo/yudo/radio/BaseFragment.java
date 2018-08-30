package com.pateo.yudo.radio;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pateo.yudo.radio.dispatcher.RxFlux;
import com.pateo.yudo.radio.dispatcher.RxViewDispatch;
import com.pateo.yudo.radio.dispatcher.action.RxError;
import com.pateo.yudo.radio.dispatcher.store.RxStore;
import com.pateo.yudo.radio.inject.components.FragmentComponent;
import com.pateo.yudo.radio.store.BaseRxStore;
import com.pateo.yudo.radio.store.actions.ActionCreator;
import com.pateo.yudo.radio.util.Log;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by pateo on 18-5-7.
 */

public abstract class BaseFragment extends Fragment implements RxViewDispatch {
    protected final String TAG = this.getClass().getSimpleName();
    protected Context mContext;
    @Inject
    protected BaseRxStore mBaseStore;
    @Inject
    protected ActionCreator mRxActionCreator;
    protected FragmentComponent mFragmentComponent;
    @Inject
    protected RxFlux mRxFlux;
    /**
     * RadioManager.onServiceConnectSuccess的服务链接是否成功
     */
    protected boolean isServiceConnetedSuccess = false;
    private Unbinder mUnbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 依赖注入
        inject(context);
        // 注册全局store
        mBaseStore.register();
    }

    private void inject(Context context) {
        // 初始化注入器
        mFragmentComponent = ((BaseActivity) context).activityComponent().getFragmentComponent();
        // 注入Injector
        initInjector();
    }

    /**
     * mFragmentComponent.inject(this);
     */
    protected abstract void initInjector();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // 设置布局
        View rootView = inflater.inflate(getLayoutId(), container, false);
        // 绑定view
        mUnbinder = ButterKnife.bind(this, rootView);
        // view创建之后的操作
        afterCreate(savedInstanceState);
        return rootView;
    }

    /**
     * 重写onCreate
     * @param savedInstanceState
     */
    protected abstract void afterCreate(Bundle savedInstanceState);

    protected abstract int getLayoutId();

    @Override
    public void onRxError(@NonNull RxError error) {

    }

    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return null;
    }

    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return null;
    }

    @Override
    public final void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        List<RxStore> rxStoreList = getRxStoreListToUnRegister();
        if (rxStoreList != null)
            for (RxStore rxStore : rxStoreList)
                rxStore.unregister();
    }

    /**
     * 注册RxStore
     * 因为fragment不能像activity通过RxFlux根据生命周期在启动的时候,
     * 调用getRxStoreListToRegister,注册RxStore,只能手动注册
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // 注册rxstore
        List<RxStore> rxStoreList = getRxStoreListToRegister();
        if (rxStoreList != null)
            for (RxStore rxStore : rxStoreList)
                rxStore.register();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        // 注册view
        mRxFlux.getDispatcher().subscribeRxView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        // 解除view注册
        mRxFlux.getDispatcher().unsubscribeRxView(this);
    }

    /**
     * Fragment执行onStop后，就不执行rxflux监听；
     * @return if true，界面存在；else false,rxflux 已经取消注册了，不能接受相关信息，只能调用服务执行相关逻辑
     */
    protected boolean isContainRxView(){
        boolean isContain=mRxFlux.getDispatcher().isSubscribeRxView(this);
        Log.i(TAG,"isContainRxView:"+isContain);
        return isContain;
    }

}
