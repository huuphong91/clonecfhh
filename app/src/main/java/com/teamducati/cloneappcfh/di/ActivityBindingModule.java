package com.teamducati.cloneappcfh.di;

import com.teamducati.cloneappcfh.screen.main.MainActivity;
import com.teamducati.cloneappcfh.screen.main.MainModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();
}
