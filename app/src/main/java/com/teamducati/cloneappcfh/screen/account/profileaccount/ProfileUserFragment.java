package com.teamducati.cloneappcfh.screen.account.profileaccount;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.screen.account.AccountContract;
import com.teamducati.cloneappcfh.screen.account.AccountPresenter;
import com.teamducati.cloneappcfh.screen.account.loginaccount.LoginFragment;
import com.teamducati.cloneappcfh.screen.account.updateaccount.DialogUpdate;
import com.teamducati.cloneappcfh.utils.ActivityUtils;
import com.teamducati.cloneappcfh.utils.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class ProfileUserFragment extends Fragment implements AccountContract.View, View.OnClickListener {

    @BindView(R.id.btn_close)
    ImageButton mBtnClose;
    @BindView(R.id.btnLogOut)
    Button mBtnLogOut;
    @BindView(R.id.edtFirstName)
    EditText mEdtFirstName;
    @BindView(R.id.edtLastName)
    EditText mEdtLastName;
    @BindView(R.id.edtBirthDate)
    EditText mEdtBirthdate;
    @BindView(R.id.edtEmail)
    EditText mEdtEmail;
    @BindView(R.id.edtPhoneNumber)
    EditText mEdtPhoneNumber;
    @BindView(R.id.edtGender)
    EditText mEdtGender;
    @BindView(R.id.imgAvatar)
    ImageView mImageAvatar;

    private User user;
    private Unbinder unbinder;
    private AccountContract.Presenter mPresenter;
    private Uri filePath;
    private User userObj;
    private LoginFragment loginFragment;
//    private FirebaseStorage storage;
//    private StorageReference storageReference;

    public ProfileUserFragment() {
    }

    public static ProfileUserFragment newInstance() {
        return new ProfileUserFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);
        unbinder = ButterKnife.bind(this, view);
        initPresenter();
        initData();
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(User event) {
        //data change
        initData();
    }

    private void initPresenter() {
        mPresenter = new AccountPresenter(getActivity(), this);
    }

    private void initData() {
        userObj = new User();
        userObj = ActivityUtils.getDataObject(getActivity(), new User().getClass());
        if (!(userObj == null)) {
            showProfileView();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEventsClick();
    }

    private void setEventsClick() {
        mBtnClose.setVisibility(View.GONE);
        mBtnClose.setOnClickListener(this);
        mBtnLogOut.setOnClickListener(this);
        mEdtFirstName.setOnClickListener(this);
        mEdtLastName.setOnClickListener(this);
        mEdtBirthdate.setOnClickListener(this);
//        mEdtEmail.setOnClickListener(this);
        mEdtPhoneNumber.setOnClickListener(this);
        mEdtGender.setOnClickListener(this);
        mImageAvatar.setOnClickListener(this);
    }

    @Override
    public void showProfileView() {
        this.user = userObj;
        mEdtFirstName.setText(user.getFirstName());
        mEdtLastName.setText(user.getLastName());
        mEdtBirthdate.setText(user.getBirthday());
        mEdtEmail.setText(user.getEmail());
        mEdtPhoneNumber.setText(user.getPhoneNumber());
        mEdtGender.setText(user.getGender());
        Glide.with(getActivity())
                .load(user.getImgAvatarUrl()).into(mImageAvatar);
    }

    @Override
    public void showLoginView() {
        loginFragment = LoginFragment.newInstance();
        ActivityUtils.addFragmentToActivity(Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                loginFragment, R.id.contentAccountFrame);
        loginFragment.setPresenter(mPresenter);
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
        ActivityUtils.removeFragmentDisplay(getActivity().getSupportFragmentManager(),this);
        showLoginView();

    }


    @Override
    public void setPresenter(AccountContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                mPresenter.onLogoutAccount();
                break;
            case R.id.btnLogOut:
                mPresenter.onLogoutAccount();
                break;
            case R.id.edtFirstName:
                DialogUpdate.newInstance(user, "First name").show(getFragmentManager(), "Update");
                break;
            case R.id.edtBirthDate:
                DialogUpdate.newInstance(user, "Birthdate").show(getFragmentManager(), "Update");
                break;
//            case R.id.edtEmail:
//                DialogUpdate.newInstance(user, "Email").show(getFragmentManager(), "Update");
//                break;
            case R.id.edtGender:
                DialogUpdate.newInstance(user, "Gender").show(getFragmentManager(), "Update");
                break;
            case R.id.edtPhoneNumber:
                DialogUpdate.newInstance(user, "Phone number").show(getFragmentManager(), "Update");
                break;
            case R.id.edtLastName:
                DialogUpdate.newInstance(user, "Last name").show(getFragmentManager(), "Update");
                break;
            case R.id.imgAvatar:
                chooseImage();

                break;
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constants.PICK_IMAGE);
        uploadImage();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.PICK_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(ProfileUserFragment.this.getContext().
                        getContentResolver(), filePath);
                mImageAvatar.setImageBitmap(bitmap);
//                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
//        storage = FirebaseStorage.getInstance();
//        storageReference = storage.getReference();
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

//            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
//            ref.putFile(filePath)
//                    .addOnSuccessListener(taskSnapshot -> {
//                        progressDialog.dismiss();
//                        Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
//                    })
//                    .addOnFailureListener(e -> {
//                        progressDialog.dismiss();
//                        Toast.makeText(getActivity(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    })
//                    .addOnProgressListener(taskSnapshot -> {
//                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
//                                .getTotalByteCount());
//                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
//                    });
        }
    }
}