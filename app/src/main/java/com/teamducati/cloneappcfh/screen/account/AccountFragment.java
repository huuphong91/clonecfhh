package com.teamducati.cloneappcfh.screen.account;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.screen.account.loginaccount.LoginFragment;
import com.teamducati.cloneappcfh.screen.account.profileaccount.ProfileUserFragment;
import com.teamducati.cloneappcfh.utils.ActivityUtils;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void showLoginView() {
        if (getContext() != null) {
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                    loginFragment, R.id.contentAccountFrame);
            loginFragment.setPresenter(mPresenter);
        }
    }

    @Override
    public void showProfileView() {
        if (getContext() != null) {
            profileUserFragment = ProfileUserFragment.newInstance();
            profileUserFragment.setPresenter(mPresenter);
            ActivityUtils.addFragmentToActivity(getActivity().getSupportFragmentManager(), profileUserFragment,
                    R.id.contentAccountFrame);
        }
    }

    @Override
    public void showDialogView() {

    }

    @Override
    public void showLoginSuccess() {

    }

    @Override
    public void showLoginFailed(String error) {

    }

    @Override
    public void showProfileSuccess() {

    }

    @Override
    public void showProfileFailed(String error) {

    }

    @Override
    public void showUpdateSuccess() {

    }

    @Override
    public void showUpdateFailed(String error) {

    }

    @Override
    public void restartViewAccount() {
        ActivityUtils.restartAllFragmentDisplay(getActivity());
    }


    @Override
    public void setPresenter(AccountContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
