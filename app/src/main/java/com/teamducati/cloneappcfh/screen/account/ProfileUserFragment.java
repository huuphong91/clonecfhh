package com.teamducati.cloneappcfh.screen.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.screen.main.MainActivity;
import com.teamducati.cloneappcfh.utils.Constants;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

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
    private FirebaseStorage storage;
    private StorageReference storageReference;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEventsClick();
    }

    private void setEventsClick() {
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
    public void showUserDetail(User user) {
        mEdtFirstName.setText(user.getFirstName());
        mEdtLastName.setText(user.getLastName());
        mEdtBirthdate.setText(user.getBirthday());
        mEdtEmail.setText(user.getEmail());
        mEdtPhoneNumber.setText(user.getPhoneNumber());
        mEdtGender.setText(user.getGender());
        Glide.with(getActivity())
                .load(user.getImgAvatarUrl()).into(mImageAvatar);
        this.user = user;
    }

    @Override
    public void showLoginFail(String whyFail) {

    }

    @Override
    public void showUpdateUserPropertySuccess() {
        Toast.makeText(getActivity(), "Updated successfull", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                mPresenter.onLogout();
                break;
            case R.id.btnLogOut:
                mPresenter.onLogout();
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

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(taskSnapshot -> {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                    });
        }
    }
}