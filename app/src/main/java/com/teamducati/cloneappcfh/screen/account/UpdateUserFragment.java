package com.teamducati.cloneappcfh.screen.account;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.User;

public class UpdateUserFragment extends Fragment implements AccountContract.View {


    public UpdateUserFragment() {
    }


    public static UpdateUserFragment newInstance() {
        return new UpdateUserFragment();
    }

    @Override
    public void showUserDetail(User user) {

    }

    @Override
    public void showLoginFail(String whyFail) {

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

    }
}
