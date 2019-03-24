package com.teamducati.cloneappcfh.screen.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.di.FragmentScoped;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.utils.ActivityUtils;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;

public class LoginFragment extends Fragment {

    @BindView(R.id.btnDialogLogin)
    Button mBtnLogin;

    private DialogLoginFragment dialogLoginFragment;

    private Unbinder unbinder;

    @Inject
    public LoginFragment(){}

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        mBtnLogin.setOnClickListener(v -> {
            dialogLoginFragment = DialogLoginFragment_Factory.newDialogLoginFragment();
            dialogLoginFragment.show(Objects.requireNonNull(getChildFragmentManager()), "dialoglogin");
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
