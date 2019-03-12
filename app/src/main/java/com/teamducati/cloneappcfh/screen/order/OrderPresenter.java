package com.teamducati.cloneappcfh.screen.order;

public class OrderPresenter implements OrderContract.Presenter {

    private OrderContract.View mOrderView;

    public OrderPresenter(OrderContract.View orderView) {
        this.mOrderView = orderView;
        mOrderView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
