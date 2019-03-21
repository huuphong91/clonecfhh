package com.teamducati.cloneappcfh.screen.account.updateaccount;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.screen.account.AccountContract;
import com.teamducati.cloneappcfh.screen.account.AccountPresenter;
import com.teamducati.cloneappcfh.utils.Constants;
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

    public DialogUpdate() {

    }

    public static DialogUpdate newInstance(User user, String title) {
        DialogUpdate dialogUpdate = new DialogUpdate();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_BUNDLE_TITLE_UPDATE_USER, title);
        bundle.putParcelable(Constants.KEY_BUNDLE_UPDATE_USER, user);
        dialogUpdate.setArguments(bundle);
        return dialogUpdate;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (getArguments() != null) {
            title = getArguments().getString(Constants.KEY_BUNDLE_TITLE_UPDATE_USER);
            user = getArguments().getParcelable(Constants.KEY_BUNDLE_UPDATE_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.dialog_update_profile, null);
        unbinder = ButterKnife.bind(this, view);
        initPresenter();
        return view;
    }

    private void initPresenter() {
        mPresenter = new AccountPresenter(getContext(), this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCancelable(false);
        setProperty(title);
        mBtnCloseDialog.setOnClickListener(v -> dismiss());
        mBtnUpdateDialog.setOnClickListener(v -> {
            updateProperty(title);
            mPresenter.onUpdateAccount(user);
            dismiss();

        });
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

    }


    @Override
    public void setPresenter(AccountContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
