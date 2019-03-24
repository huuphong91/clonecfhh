package com.teamducati.cloneappcfh.screen.account;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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
import com.teamducati.cloneappcfh.utils.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class ProfileUserFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "Profile";
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

    private Unbinder unbinder;

    private User user;

    private Uri filePath;
    private User userObj;
//    private FirebaseStorage storage;
//    private StorageReference storageReference;

    @Inject
    public ProfileUserFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        User user = null;
        if (bundle != null) {
            user = bundle.getParcelable("User");
        }
        getUserInfo(user);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(User event) {
       getUserInfo(event);
    }

    private void getUserInfo(User user) {
        if (user != null) {
            this.user = user;
            mEdtFirstName.setText(user.getFirstName());
            mEdtLastName.setText(user.getLastName());
            mEdtBirthdate.setText(user.getBirthday());
            mEdtEmail.setText(user.getEmail());
            mEdtPhoneNumber.setText(user.getPhoneNumber());
            mEdtGender.setText(user.getGender());
            loadImage(convertStringToBitmap(user.getImgAvatarUrl()),mImageAvatar);
        }
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
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Log.d(TAG, "onDestroyView");
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                sendBroadCastLogOut();
                break;
            case R.id.btnLogOut:
                sendBroadCastLogOut();
                break;
            case R.id.edtFirstName:
                DialogUpdate.newInstance(user, "First name").show(getChildFragmentManager(), "Update");
                break;
            case R.id.edtBirthDate:
                DialogUpdate.newInstance(user, "Birthdate").show(getChildFragmentManager(), "Update");
                break;
//            case R.id.edtEmail:
//                DialogUpdate.newInstance(user, "Email").show(getFragmentManager(), "Update");
//                break;
            case R.id.edtGender:
                DialogUpdate.newInstance(user, "Gender").show(getChildFragmentManager(), "Update");
                break;
            case R.id.edtPhoneNumber:
                DialogUpdate.newInstance(user, "Phone number").show(getChildFragmentManager(), "Update");
                break;
            case R.id.edtLastName:
                DialogUpdate.newInstance(user, "Last name").show(getChildFragmentManager(), "Update");
                break;
            case R.id.imgAvatar:
                chooseImage();
                Bitmap bm=((BitmapDrawable)mImageAvatar.getDrawable()).getBitmap();
                uploadImage(bm);
                break;
        }
    }

    private void sendBroadCastLogOut() {
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext()))
                .sendBroadcast(new Intent(Constants.ACTION_LOG_OUT));
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constants.PICK_IMAGE);
    }

    private void uploadImage(Bitmap bitmap) {
        user.setImgAvatarUrl(convertBitmapToString(bitmap));
//        mPresenter.updateUserProperty(user);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.PICK_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
               Bitmap bitmapPickImage = MediaStore.Images.Media.getBitmap(ProfileUserFragment.this.getContext().
                        getContentResolver(), filePath);
                uploadImage(bitmapPickImage);
                mImageAvatar.setImageBitmap(bitmapPickImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap convertStringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public void loadImage(Bitmap bitmap, ImageView imageView) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Glide.with(this)
                .asBitmap()
                .load(stream.toByteArray())
                .into(imageView);
    }
}