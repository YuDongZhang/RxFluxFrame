package com.pateo.yudo.radio.store.actions;

import com.pateo.yudo.radio.config.ActionKeys;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

/**
 * Created by pateo on 18-5-11.
 */

public interface Actions extends ActionKeys {

    void postBaseAction(@NonNull String actionId, @NonNull Object... data);
    void postSycnAction(@NonNull String actionId, @NonNull Object... data);
}
