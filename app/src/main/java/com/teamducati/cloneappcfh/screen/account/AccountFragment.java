package com.teamducati.cloneappcfh.screen.account;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.di.ActivityScoped;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.screen.main.UserBroadCast;
import com.teamducati.cloneappcfh.screen.main.UserBroadCast_Factory;
import com.teamducati.cloneappcfh.utils.ActivityUtils;
import com.teamducati.cloneappcfh.utils.Constants;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;

@ActivityScoped
public class AccountFragment extends DaggerFragment implements AccountContract.View {

    private static final String TAG = "AccountFragment";
    @BindView(R.id.contentAccountFrame)
    FrameLayout mContentAccountFrame;

    private Unbinder unbinder;

    private ProfileUserFragment profileUserFragment;
    private LoginFragment loginFragment;

    private UserBroadCast userBroadcast;

    @Inject
    AccountContract.Presenter mPresenter;

    @Inject
    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        unbinder = ButterKnife.bind(this, view);

        userBroadcast = UserBroadCast_Factory.newUserBroadCast(this);

        profileUserFragment = (ProfileUserFragment) Objects.requireNonNull(getActivity())
                .getSupportFragmentManager()
                .findFragmentById(R.id.contentAccountFrame);

        loginFragment = (LoginFragment) Objects.requireNonNull(getActivity())
                .getSupportFragmentManager()
                .findFragmentById(R.id.contentAccountFrame);

        if (loginFragment == null) {
            loginFragment = LoginFragment_Factory.newLoginFragment();
            ActivityUtils.addFragmentToActivity(getActivity().getSupportFragmentManager(), loginFragment, R.id.contentAccountFrame);
        }
        Log.d(TAG, "onCreateView");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActionAndRegisterReceiver();
        Log.d(TAG, "onStart");
    }

    private void getActionAndRegisterReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constants.ACTION_USER_RESULT);
        intentFilter.addAction(Constants.ACTION_LOG_OUT);
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext()))
                .registerReceiver(userBroadcast, intentFilter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
        Log.d(TAG, "onResume");
    }


    @Override
    public void showUserDetail(User user) {

        if (profileUserFragment == null) {
            profileUserFragment = ProfileUserFragment_Factory.newProfileUserFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("User", user);
            profileUserFragment.setArguments(bundle);
            ActivityUtils.chooseFragmentWannaDisplay(Objects.requireNonNull(getActivity())
                    .getSupportFragmentManager(), profileUserFragment, R.id.contentAccountFrame);
        } else {
            Bundle bundle = new Bundle();
            bundle.putParcelable("User", user);
            profileUserFragment.setArguments(bundle);
            ActivityUtils.chooseFragmentWannaDisplay(Objects.requireNonNull(getActivity())
                    .getSupportFragmentManager(), profileUserFragment, R.id.contentAccountFrame);
        }
    }

    @Override
    public void showLoginScreen() {
        if (loginFragment == null) {
            loginFragment = LoginFragment_Factory.newLoginFragment();
            ActivityUtils.chooseFragmentWannaDisplay(Objects.requireNonNull(getActivity())
                    .getSupportFragmentManager(), loginFragment, R.id.contentAccountFrame);
        } else {
            ActivityUtils.chooseFragmentWannaDisplay(Objects.requireNonNull(getActivity())
                    .getSupportFragmentManager(), loginFragment, R.id.contentAccountFrame);
        }
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
        Log.d(TAG, "onDestroyView");
        unbinder.unbind();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext())).unregisterReceiver(userBroadcast);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        mPresenter.dropView();
    }
}
