package com.pateo.yudo.radio.inject.components;

import com.pateo.yudo.radio.inject.PerFragment;
import com.pateo.yudo.radio.inject.modules.FragmentModule;
import com.pateo.yudo.radio.ui.DemoFragment;

import dagger.Subcomponent;

/**
 * Created by pateo on 18-5-11.
 */

@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(DemoFragment scanRadioFragment);
}

