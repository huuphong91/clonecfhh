package com.teamducati.cloneappcfh.screen.account;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.di.ActivityScoped;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.utils.ActivityUtils;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Insert;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.Lazy;
import dagger.android.support.DaggerFragment;

@ActivityScoped
public class AccountFragment extends DaggerFragment implements AccountContract.View {

    @BindView(R.id.contentAccountFrame)
    FrameLayout mContentAccountFrame;

    private Unbinder unbinder;

    private ProfileUserFragment profileUserFragment;
    private LoginFragment loginFragment;

    @Inject
    AccountContract.Presenter mPresenter;

    @Inject
    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        unbinder = ButterKnife.bind(this, view);
        addFragmentToActivity();
        return view;
    }

    private void addFragmentToActivity() {
        ProfileUserFragment profileUserFragment = (ProfileUserFragment) Objects.requireNonNull(getActivity())
                .getSupportFragmentManager()
                .findFragmentById(R.id.contentAccountFrame);
        if (profileUserFragment == null) {
            profileUserFragment = ProfileUserFragment_Factory.newProfileUserFragment();
            ActivityUtils.addFragmentToActivity(Objects.requireNonNull(getActivity())
                    .getSupportFragmentManager(),profileUserFragment,R.id.contentAccountFrame);
        }

        LoginFragment loginFragment = (LoginFragment) Objects.requireNonNull(getActivity())
                .getSupportFragmentManager()
                .findFragmentById(R.id.contentAccountFrame);
        if (loginFragment == null) {
            loginFragment = LoginFragment_Factory.newLoginFragment();
            ActivityUtils.addFragmentToActivity(Objects.requireNonNull(getActivity())
                    .getSupportFragmentManager(),loginFragment,R.id.contentAccountFrame);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }


    @Override
    public void showUserDetail() {
        ActivityUtils.chooseFragmentWannaDisplay(Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                ,profileUserFragment
                ,R.id.contentAccountFrame);
    }

    @Override
    public void showLoginScreen() {
        ActivityUtils.chooseFragmentWannaDisplay(Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                ,loginFragment
                ,R.id.contentAccountFrame);
    }

    @Override
    public void restartViewAccount() {
        ActivityUtils.restartAllFragmentDisplay(getActivity());
    }

    @Override
    public void showLoginFail(String whyFail) {
//        loginFragment.showLoginFail(whyFail);
    }

    @Override
    public void showUpdateUserPropertySuccess() {
//        profileUserFragment.showUpdateUserPropertySuccess();
    }

    @Override
    public void showUpdateUserPropertyFail() {
        Toast.makeText(getActivity(), "Updated fail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }
}
