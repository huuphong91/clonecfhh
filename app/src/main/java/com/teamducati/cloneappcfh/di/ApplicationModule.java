package com.teamducati.cloneappcfh.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ApplicationModule {

    @Binds
    abstract Context bindContext(Application application);
}