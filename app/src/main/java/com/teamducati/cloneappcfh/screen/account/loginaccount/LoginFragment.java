package com.teamducati.cloneappcfh.screen.account.loginaccount;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.screen.account.AccountContract;
import com.teamducati.cloneappcfh.screen.account.AccountPresenter;
import com.teamducati.cloneappcfh.screen.account.profileaccount.ProfileUserFragment;
import com.teamducati.cloneappcfh.screen.account.profileaccount.ProfileUserFragment;
import com.teamducati.cloneappcfh.utils.ActivityUtils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoginFragment extends Fragment implements AccountContract.View {

    @BindView(R.id.btnLogin)
    Button mBtnLogin;
    @BindView(R.id.edt_username)
    EditText mEdtUsername;
    @BindView(R.id.edt_password)
    EditText mEdtPassword;

    private User userLogin;
    private Unbinder unbinder;

    private AccountContract.Presenter mPresenter;
    private ProfileUserFragment profileUserFragment;

    public LoginFragment(){}

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        initPresenter();
        return view;
    }

    private void initPresenter() {
        mPresenter=new AccountPresenter(getActivity(),this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBtnLogin.setOnClickListener(v -> {
            String username = mEdtUsername.getText().toString();
            String password = mEdtPassword.getText().toString();
            userLogin= new User();
            userLogin.setUserName(username);
            userLogin.setPassword(password);
            mPresenter.onLoginAccount(userLogin);
        });
    }


    @Override
    public void showLoginView() {

    }

    @Override
    public void showProfileView() {

    }

    @Override
    public void showDialogView() {

    }

    @Override
    public void showLoginSuccess() {

        if (getContext() != null) {
            ActivityUtils.removeFragmentDisplay(getActivity().getSupportFragmentManager(),this);
            profileUserFragment = ProfileUserFragment.newInstance();
            profileUserFragment.setPresenter(mPresenter);
            ActivityUtils.addFragmentToActivity(getActivity().getSupportFragmentManager(), profileUserFragment,
                    R.id.contentAccountFrame);
        }
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
        ActivityUtils.removeFragmentDisplay(getActivity().getSupportFragmentManager(),this);
//        ActivityUtils.restartAllFragmentDisplay(getActivity());
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
