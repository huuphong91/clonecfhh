package com.teamducati.cloneappcfh.screen.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.User;
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

    public LoginFragment(){}

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBtnLogin.setOnClickListener(v -> {
            String username = mEdtUsername.getText().toString();
            String password = mEdtPassword.getText().toString();
//            validateField(username, password);
            userLogin= new User();
            userLogin.setUserName(username);
            userLogin.setPassword(password);
            mPresenter.onLogin(userLogin);
        });
    }

    @Override
    public void showUserDetail() {

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
    public void showLoginFail(String whyFail) {
        Toast.makeText(getContext(), whyFail, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showUpdateUserPropertySuccess( ) {
        Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUpdateUserPropertyFail() {
        Toast.makeText(getContext(), "Updated unsuccessfully", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showLoginScreen() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
