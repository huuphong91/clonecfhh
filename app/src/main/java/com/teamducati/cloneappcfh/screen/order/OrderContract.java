package com.teamducati.cloneappcfh.screen.order;

import com.teamducati.cloneappcfh.entity.api_order.ItemProductResponse;
import com.teamducati.cloneappcfh.utils.BasePresenter;
import com.teamducati.cloneappcfh.utils.BaseView;

public interface OrderContract {

    interface Presenter extends BasePresenter {
        void onGetAllProductPresenter();
    }

    interface View extends BaseView<Presenter> {
        void onGetAllProductView(ItemProductResponse itemListProductResponse);

        void displayError(String s);
    }
}
