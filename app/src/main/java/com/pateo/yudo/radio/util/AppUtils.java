package com.pateo.yudo.radio.util;

import android.app.Application;
import android.support.annotation.NonNull;

import com.pateo.yudo.radio.inject.components.ApplicationComponent;

public class AppUtils {
    private static ApplicationComponent sApplicationComponent;
    private static Application sApplication;

    public static void setApplicationComponent(@NonNull ApplicationComponent applicationComponent) {
        sApplicationComponent = applicationComponent;
    }

    public static ApplicationComponent getApplicationComponent() {
        return sApplicationComponent;
    }

    public static Application getApplication() {
        return sApplication;
    }

    public static void setApplication(Application application) {
        sApplication = application;
    }


}
