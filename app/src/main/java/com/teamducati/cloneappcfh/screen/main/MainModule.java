package com.teamducati.cloneappcfh.screen.main;

import com.teamducati.cloneappcfh.di.ActivityScoped;
import com.teamducati.cloneappcfh.di.FragmentScoped;
import com.teamducati.cloneappcfh.screen.account.AccountContract;
import com.teamducati.cloneappcfh.screen.account.AccountFragment;
import com.teamducati.cloneappcfh.screen.account.AccountModule;
import com.teamducati.cloneappcfh.screen.account.AccountPresenter;
import com.teamducati.cloneappcfh.screen.account.LoginFragment;
import com.teamducati.cloneappcfh.screen.account.ProfileUserFragment;
import com.teamducati.cloneappcfh.screen.news.NewsContract;
import com.teamducati.cloneappcfh.screen.news.NewsFragment;
import com.teamducati.cloneappcfh.screen.news.NewsModule;
import com.teamducati.cloneappcfh.screen.news.NewsPresenter;
import com.teamducati.cloneappcfh.screen.order.OrderContract;
import com.teamducati.cloneappcfh.screen.order.OrderFragment;
import com.teamducati.cloneappcfh.screen.order.OrderModule;
import com.teamducati.cloneappcfh.screen.order.OrderPresenter;
import com.teamducati.cloneappcfh.screen.order.shipaddressrepick.ShipAddressRepickContract;
import com.teamducati.cloneappcfh.screen.order.shipaddressrepick.ShipAddressRepickPresenter;
import com.teamducati.cloneappcfh.screen.store.StoreContract;
import com.teamducati.cloneappcfh.screen.store.StoreFragment;
import com.teamducati.cloneappcfh.screen.store.StoreModule;
import com.teamducati.cloneappcfh.screen.store.StorePresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = NewsModule.class)
    abstract NewsFragment newsFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = OrderModule.class)
    abstract OrderFragment orderFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = StoreModule.class)
    abstract StoreFragment storeFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = AccountModule.class)
    abstract AccountFragment accountFragment();

    @ActivityScoped
    @Binds
    abstract NewsContract.Presenter newsPresenter(NewsPresenter newsPresenter);

    @ActivityScoped
    @Binds
    abstract OrderContract.Presenter orderPresenter(OrderPresenter orderPresenter);

    @ActivityScoped
    @Binds
    abstract StoreContract.Presenter storePresenter(StorePresenter storePresenter);

    @ActivityScoped
    @Binds
    abstract AccountContract.Presenter accountPresenter(AccountPresenter accountPresenter);
}
