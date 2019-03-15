package com.example.thecoffeehouse.dialog;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import com.example.thecoffeehouse.R;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.ByteArrayOutputStream;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

public class FirstUpdateInforFragment extends DialogFragment {

    private Toolbar toolbar;
    private AppCompatActivity activity;
    private TextInputEditText mEditTextLastName;
    private TextInputEditText mEditTextFirstName;
    private Button mButtonCommit;
    private String numberPhone;
    private ImageView mImageViewDelete;
    private DatabaseReference mDataRef;
    private ProgressDialog uploadDialog;

    public static FirstUpdateInforFragment newInstance() {
        FirstUpdateInforFragment fragment = new FirstUpdateInforFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_update_infor, container, false);
        toolbar = view.findViewById(R.id.frag_update_toolbar);
        toolbar.setTitle("");
        activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        mEditTextFirstName = view.findViewById(R.id.frag_update_edt_firstname);
        mEditTextLastName = view.findViewById(R.id.frag_update_edt_lastname);
        mButtonCommit = view.findViewById(R.id.frag_update_btn_commit);
        mImageViewDelete = view.findViewById(R.id.frag_update_img_delete);
        mDataRef = FirebaseDatabase.getInstance().getReference("Users");
        uploadDialog = new ProgressDialog(activity);
        uploadDialog.setTitle("Uploading Your InFor");
        uploadDialog.setMessage("Please, Wait!");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
    }

    @Override
    public void onResume() {
        super.onResume();
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                numberPhone = "0" + account.getPhoneNumber().getPhoneNumber();

            }

            @Override
            public void onError(AccountKitError accountKitError) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addEvents();
    }

    private void addEvents() {
        mButtonCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadDialog.show();
                insertUser();
            }
        });

        mImageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstUpdateInforFragment.this.dismiss();
            }
        });
    }

    private void insertUser() {
        String lastName = mEditTextLastName.getText().toString().trim();
        String firstName = mEditTextFirstName.getText().toString().trim();


        Bitmap imageBitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.img_add_photo);
        String image = BitMapToString(imageBitmap);
        com.example.thecoffeehouse.user.User user = new com.example.thecoffeehouse.user.User(firstName, lastName, "[Chưa xác định]", numberPhone, "[Chưa xác định]", "", image);
        mDataRef.child(numberPhone).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                uploadDialog.dismiss();
                FirstUpdateInforFragment.this.dismiss();
            }
        });
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

}
