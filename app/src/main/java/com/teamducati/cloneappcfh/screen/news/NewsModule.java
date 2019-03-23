package com.teamducati.cloneappcfh.screen.news;

import com.teamducati.cloneappcfh.screen.news.adapter.NewsListAdapter;
import com.teamducati.cloneappcfh.screen.news.adapter.NewsPromotionListAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class NewsModule {

    @Provides
    static NewsListAdapter newsListAdapter() {
        return new NewsListAdapter();
    }

    @Provides
    static NewsPromotionListAdapter newsPromotionListAdapter() {
        return new NewsPromotionListAdapter();
    }
}
