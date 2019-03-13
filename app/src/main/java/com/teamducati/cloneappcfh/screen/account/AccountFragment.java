package com.teamducati.cloneappcfh.screen.account;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.screen.news.NewsContract;
import com.teamducati.cloneappcfh.utils.ActivityUtils;

import java.util.Objects;

public class AccountFragment extends Fragment implements AccountContract.View {

    @BindView(R.id.contentAccountFrame)
    FrameLayout mContentAccountFrame;

    private Unbinder unbinder;
    private LoginFragment loginFragment;
    private ProfileUserFragment profileUserFragment;
    private AccountContract.Presenter mPresenter;


    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        profileUserFragment = (ProfileUserFragment) Objects.requireNonNull(getActivity())
                .getSupportFragmentManager()
                .findFragmentById(R.id.contentAccountFrame);
        if (profileUserFragment == null) {
            profileUserFragment = ProfileUserFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getActivity().getSupportFragmentManager(), profileUserFragment, R.id.contentAccountFrame);
        }
        loginFragment = (LoginFragment) Objects.requireNonNull(getActivity())
                .getSupportFragmentManager()
                .findFragmentById(R.id.contentAccountFrame);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getActivity().getSupportFragmentManager(), loginFragment, R.id.contentAccountFrame);
        }
        ActivityUtils.chooseFragmentWannaDisplay(getActivity().getSupportFragmentManager(), loginFragment, R.id.contentAccountFrame);
        loginFragment.setPresenter(mPresenter);
        profileUserFragment.setPresenter(mPresenter);
    }

    @Override
    public void showUserDetail(User user) {
        ActivityUtils.chooseFragmentWannaDisplay(Objects.requireNonNull(getActivity())
                .getSupportFragmentManager(), profileUserFragment, R.id.contentAccountFrame);
    }

    @Override
    public void showLoginFail(String whyFail) {
        loginFragment.showLoginFail(whyFail);
    }

    @Override
    public void showUpdateUserPropertySuccess() {

    }

    @Override
    public void showUpdateUserPropertyFail() {

    }

    @Override
    public void showLoginScreen() {
        ActivityUtils.chooseFragmentWannaDisplay(Objects.requireNonNull(getActivity()).getSupportFragmentManager(),loginFragment, R.id.contentAccountFrame);

    }

    @Override
    public void setPresenter(AccountContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
