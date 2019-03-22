package com.teamducati.cloneappcfh.screen.account;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.utils.ActivityUtils;
import com.teamducati.cloneappcfh.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DialogUpdate extends DialogFragment implements AccountContract.View {

    @BindView(R.id.edtPropertyDialog)
    EditText mEdtPropertyDialog;
    @BindView(R.id.tvPropertyDialog)
    TextView mTvPropertyDialog;
    @BindView(R.id.btnUpdateDialog)
    Button mBtnUpdateDialog;
    @BindView(R.id.ibtnCloseDialog)
    ImageButton mBtnCloseDialog;

    private Unbinder unbinder;
    private User user;
    private String title;
    private AccountContract.Presenter mPresenter;

    private static DialogUpdate sInstance;

    public static DialogUpdate getInstance() {
        if (sInstance == null) {
            sInstance = new DialogUpdate();
        }
        return sInstance;
    }

    public void setDemo(User user, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_BUNDLE_TITLE_UPDATE_USER, title);
        bundle.putParcelable(Constants.KEY_BUNDLE_UPDATE_USER, user);
        sInstance.setArguments(bundle);
    }

    private static DialogUpdate destroyInstance() {
        if (sInstance != null) {
            sInstance = null;
        }
        return sInstance;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.dialog_update_profile, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCancelable(false);
        mBtnCloseDialog.setOnClickListener(v -> dismiss());
        mBtnUpdateDialog.setOnClickListener(v -> {
            updateProperty(title);
            mPresenter.updateUserProperty(user);
            dismiss();

        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            title = getArguments().getString(Constants.KEY_BUNDLE_TITLE_UPDATE_USER);
            user = getArguments().getParcelable(Constants.KEY_BUNDLE_UPDATE_USER);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setProperty(title);
    }

    private void setProperty(String title) {
        if (title.equals("First name")) {
            mEdtPropertyDialog.setText(user.getFirstName().trim());
            mTvPropertyDialog.setText(title);
        }
        if (title.equals("Last name")) {
            mEdtPropertyDialog.setText(user.getLastName().trim());
            mTvPropertyDialog.setText(title);
        }
        if (title.equals("Email")) {
            mEdtPropertyDialog.setText(user.getEmail());
            mTvPropertyDialog.setText(title);
        }
        if (title.equals("Birthdate")) {
            mEdtPropertyDialog.setText(user.getBirthday());
            mTvPropertyDialog.setText(title);
        }
        if (title.equals("Phone number")) {
            mEdtPropertyDialog.setText(user.getPhoneNumber());
            mTvPropertyDialog.setText(title);
        }
        if (title.equals("Gender")) {
            mEdtPropertyDialog.setText(user.getGender());
            mTvPropertyDialog.setText(title);
        }
    }

    private void updateProperty(String title) {
        if (title.equals("First name")) {
            user.setFirstName(mEdtPropertyDialog.getText().toString());
        }
        if (title.equals("Last name")) {
            user.setLastName(mEdtPropertyDialog.getText().toString());
        }
        if (title.equals("Email")) {
            user.setEmail(mEdtPropertyDialog.getText().toString());
        }
        if (title.equals("Birthdate")) {
            user.setBirthday(mEdtPropertyDialog.getText().toString());
        }
        if (title.equals("Phone number")) {
            user.setPhoneNumber(mEdtPropertyDialog.getText().toString());
        }
        if (title.equals("Gender")) {
            user.setGender(mEdtPropertyDialog.getText().toString());
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void showUserDetail() {

    }

    @Override
    public void showLoginScreen() {

    }

    @Override
    public void restartViewAccount() {

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
    public void setPresenter(AccountContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
