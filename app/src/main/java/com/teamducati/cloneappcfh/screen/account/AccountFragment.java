package com.teamducati.cloneappcfh.screen.account;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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

public class AccountFragment extends Fragment implements AccountContract.View.AccountView {

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
        initPresenter();
        return view;
    }

    private void initPresenter() {
        mPresenter = new AccountPresenter(getContext(), this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void showLoginView() {
        Log.d("LifeCycleTest", "Account Fragment Event Show Login");

        if (getContext() != null) {
            loginFragment = LoginFragment.newInstance();
            loginFragment.setPresenter(mPresenter);
            ActivityUtils.addFragmentToActivity(Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                    loginFragment, R.id.contentAccountFrame);
        }
    }

    @Override
    public void showProfileView() {
        Log.d("LifeCycleTest", "Account Fragment Event Show Profile");

        if (getContext() != null) {
            profileUserFragment = ProfileUserFragment.newInstance();
            profileUserFragment.setPresenter(mPresenter);
            ActivityUtils.addFragmentToActivity(getActivity().getSupportFragmentManager(), profileUserFragment,
                    R.id.contentAccountFrame);
        }
    }

    @Override
    public void onStart() {
        mPresenter.onCheckDataAccount();
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(AccountContract.Presenter presenter) {
        mPresenter = presenter;
    }


}
