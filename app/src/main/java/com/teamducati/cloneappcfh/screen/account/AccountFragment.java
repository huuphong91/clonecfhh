package com.teamducati.cloneappcfh.screen.account;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.User;
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
        if (profileUserFragment == null) {
            profileUserFragment = ProfileUserFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getActivity().getSupportFragmentManager(), profileUserFragment, R.id.contentAccountFrame);
            profileUserFragment.setPresenter(mPresenter);
        }
        loginFragment = (LoginFragment) Objects.requireNonNull(getActivity())
                .getSupportFragmentManager()
                .findFragmentById(R.id.contentAccountFrame);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getActivity().getSupportFragmentManager(), loginFragment, R.id.contentAccountFrame);
            loginFragment.setPresenter(mPresenter);
        }

    }

    @Override
    public void showUserDetail(User user) {
        profileUserFragment.showUserDetail(user);
        ActivityUtils.chooseFragmentWannaDisplay(Objects.requireNonNull(getActivity())
                .getSupportFragmentManager(), profileUserFragment, R.id.contentAccountFrame);
    }

    @Override
    public void showLoginFail(String whyFail) {
        loginFragment.showLoginFail(whyFail);
    }

    @Override
    public void showUpdateUserPropertySuccess() {
        profileUserFragment.showUpdateUserPropertySuccess();
    }

    @Override
    public void showUpdateUserPropertyFail() {
        Toast.makeText(getActivity(), "Updated fail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginScreen() {
        loginFragment = LoginFragment.newInstance();
        ActivityUtils.addFragmentToActivity(Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                loginFragment, R.id.contentAccountFrame);
        loginFragment.setPresenter(mPresenter);
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
