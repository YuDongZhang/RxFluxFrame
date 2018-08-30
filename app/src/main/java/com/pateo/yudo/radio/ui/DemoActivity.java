package com.pateo.yudo.radio.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pateo.yudo.radio.BaseActivity;
import com.pateo.yudo.radio.R;
import com.pateo.yudo.radio.config.ActionKeys;
import com.pateo.yudo.radio.dispatcher.action.RxError;
import com.pateo.yudo.radio.dispatcher.store.RxStore;
import com.pateo.yudo.radio.dispatcher.store.RxStoreChange;
import com.pateo.yudo.radio.util.Log;

import java.util.List;

import butterknife.BindView;

/**
 * 使用步骤：
 * 1.注册Application,本例子为{@link com.pateo.yudo.radio.RadioApplications}
 *   在文件{@link com.pateo.yudo.radio.inject.components.ApplicationComponent 中修改类名}
 * 2.所有Acitivity都需要继承 {@link BaseActivity},Fragment 继承{@link com.pateo.yudo.radio.BaseFragment}
 *   并实现里面的抽象方法，父类方法{@link #injectComponent()}直接使用注释说明既可，
 *   实现的Activity需要在{@link com.pateo.yudo.radio.inject.components.ActivityComponent}注册，自行添加方法 {@link #inject()}
 *   实现的Fragment需要在{@link com.pateo.yudo.radio.inject.components.ActivityComponent}注册,自行添加方法 {@link #inject()}
 * 3.Demo里实现了一个事件发送，参考发送事件及接收事件即可！
 *   {@link DemoFragment}发送了一个事件{@link ActionKeys.DEMO_TEST_EXAMPLE}
 *   在{@link DemoActivity}接收了该事件
 *
 *  @inject 把对象注解在这里
 *  @module 看成是工厂, 提供注解的对象生产,里面有很多的 @provides里面的
 *  @Provides 提供的对象,一般会返回一个对象的实例.
 *  @component 容器 ,module产生的对象 存在里面 , 然后要将 component 与要注入的activity 关联
 *
 * 本类做的是这样的,组件的依赖
 *
 */
public class DemoActivity extends BaseActivity {
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private FragmentManager fManager;

    @Override
    public void injectComponent() {
        activityComponent().inject(this);
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        fManager = getSupportFragmentManager();
    }

    //这个方法来自于dispatcher, 来自于 baseAcitvity中RxViewDispatch
    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            //这个界面接收什么事件，就注册什么事件，然后处理
            case ActionKeys.DEMO_TEST_EXAMPLE:
                String buffer = change.getRxAction().get(ActionKeys.KEY);
                tvInfo.append(buffer + "\n");
                scrollView.scrollTo(0,tvInfo.getHeight());

                break;
        }
    }

    @Override
    public void onRxError(@NonNull RxError error) {
        Log.i(TAG, error.toString());

    }

    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return null;
    }

    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return null;
    }


}
