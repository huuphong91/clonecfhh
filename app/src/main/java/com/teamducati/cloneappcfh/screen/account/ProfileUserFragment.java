package com.teamducati.cloneappcfh.screen.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.utils.ActivityUtils;
import com.teamducati.cloneappcfh.utils.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.FileNotFoundException;
import java.io.InputStream;
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
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl(Constants.STORAGE_URL);

    private Uri imageUri;
    private ProgressDialog progressDialog;

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
            loadImage(user.getImgAvatarUrl(), mImageAvatar);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        setEventsClick();
    }

    private void initPresenter() {
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
                break;
        }
    }

    private void sendBroadCastLogOut() {
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext()))
                .sendBroadcast(new Intent(Constants.ACTION_LOG_OUT));
    }

    private void chooseImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, Constants.PICK_IMAGE);
    }

    public void updateUserProperty(User userData) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.orderByChild("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myRef.child("User").setValue(userData);
                Intent userIntent = new Intent(Constants.ACTION_USER_RESULT);
                userIntent.putExtra("User", userData);
                ActivityUtils.setDataObject(getActivity(), userData);
                EventBus.getDefault().post(user);
                LocalBroadcastManager.getInstance(getActivity())
                        .sendBroadcast(userIntent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(getActivity(), "updated successful", Toast.LENGTH_SHORT).show();
    }

    public void uploadImage(Uri imageUri) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Upload Image...");
        progressDialog.show();
        if (imageUri != null) {
            // String imageName="image_"+System.currentTimeMillis()+".jpg";
            //app only one user
            String imageAvatarName = "image_avatar.jpg";
            StorageReference childRef = storageRef.child(imageAvatarName);

            //uploading the image
            UploadTask uploadTask = childRef.putFile(imageUri);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    childRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Toast.makeText(getActivity(), "Upload Image successful", Toast.LENGTH_SHORT).show();
                            user.setImgAvatarUrl(uri.toString());
                            updateUserProperty(user);
                            progressDialog.dismiss();
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Upload Image Failed -> " + e, Toast.LENGTH_SHORT).show();
                    Log.d("UploadImage", e.toString());
                    progressDialog.dismiss();
                }
            });
        } else {
            Toast.makeText(getActivity(), "Select an image", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.PICK_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            try {
                imageUri = data.getData();
                InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                uploadImage(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getActivity(), "Bạn chưa chọn ảnh", Toast.LENGTH_LONG).show();
        }
    }

    public void loadImage(String url, ImageView imageView) {
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(imageView);
    }
}