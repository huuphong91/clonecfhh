package com.teamducati.cloneappcfh.screen.account;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.screen.news.NewsContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements AccountContract.View {

    private AccountContract.Presenter mPresenter;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void showUserDetail(User user) {

    }

    @Override
    public void showLoginFail() {

    }

    @Override
    public void showUpdateUserPropertySuccess() {

    }

    @Override
    public void showUpdateUserPropertyFail() {

    }

    @Override
    public void showLoginScreen() {

    }

    @Override
    public void setPresenter(AccountContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
