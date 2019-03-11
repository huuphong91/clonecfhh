package com.teamducati.cloneappcfh.screen.store;

public class StorePresenter implements StoreContract.Presenter {

    private StoreContract.View mStoreView;

    public StorePresenter(StoreContract.View storeView) {
        this.mStoreView = storeView;
        mStoreView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
