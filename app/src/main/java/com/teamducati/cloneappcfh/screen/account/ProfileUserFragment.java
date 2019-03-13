package com.teamducati.cloneappcfh.screen.account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class ProfileUserFragment extends Fragment implements AccountContract.View {
    @BindView(R.id.btn_close) ImageButton mBtnClose;
    @BindView(R.id.btnLogOut) Button mBtnLogOut;
    @BindView(R.id.edtFirstName) EditText mEdtFirstName;
    @BindView(R.id.edtLastName) EditText mEdtLastName;
    @BindView(R.id.edtBirthDate) EditText mEdtBirthdate;
    @BindView(R.id.edtEmail) EditText mEdtEmail;
    @BindView(R.id.edtPhoneNumber) EditText mEdtPhoneNumber;
    @BindView(R.id.edtGender) EditText mEdtGender;

    private Unbinder unbinder;

    private AccountContract.Presenter mPresenter;

    public ProfileUserFragment() {
    }

    public static ProfileUserFragment newInstance() {
        return new ProfileUserFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        User user= new User();
        mBtnClose.setOnClickListener(v -> {
            mPresenter.onLogout(user);
        });
    }

    @Override
    public void showUserDetail(User user) {
        if (user != null) {
            Toast.makeText(getActivity(), user.getFirstName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
        }

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
        mPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
