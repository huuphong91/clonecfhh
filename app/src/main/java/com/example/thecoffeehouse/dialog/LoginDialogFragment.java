package com.example.thecoffeehouse.dialog;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thecoffeehouse.Constant;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.main.MainActivity;
import com.example.thecoffeehouse.user.User;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class LoginDialogFragment extends DialogFragment {

    private final static int REQUEST_CODE = 999;
    private Toolbar toolbar;
    private ImageView mImageDelete;
    private Button mButtonCommit, mButtonFB, mButtonEmail;
    private EditText mEditTextPhone;
    private AppCompatActivity activity;
    private String numberPhone;
    private FirebaseAuth mAuth;
    private DatabaseReference mDataRef;
    private FragmentManager fragmentManager;

    public static LoginDialogFragment newInstance() {
        LoginDialogFragment fragment = new LoginDialogFragment();
        return fragment;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_dialog, container, false);
        toolbar = view.findViewById(R.id.frag_login_toolbar);
        toolbar.setTitle("");
        activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        mDataRef = FirebaseDatabase.getInstance().getReference("Users");
        mImageDelete = toolbar.findViewById(R.id.frag_login_img_delete);
        mButtonCommit = view.findViewById(R.id.frag_login_btn_commit);
        mButtonFB = view.findViewById(R.id.frag_login_btn_fb);
        mButtonEmail = view.findViewById(R.id.frag_login_btn_email);
        mEditTextPhone = view.findViewById(R.id.fragment_edt_phone);
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        mImageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginDialogFragment.this.dismiss();
            }
        });

        mButtonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mButtonFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountKit.logOut();
                Intent newIntent = new Intent(activity,MainActivity.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent);
            }
        });

        mButtonCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberPhone = mEditTextPhone.getText().toString();
                startLoginPage(LoginType.PHONE, numberPhone);
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        fragmentManager = getFragmentManager();
    }

    private void startLoginPage(LoginType loginType, String numberPhone) {
        if (loginType == LoginType.EMAIL) {
            Intent i = new Intent(activity, AccountKitActivity.class);
            AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                    new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.EMAIL,
                            AccountKitActivity.ResponseType.TOKEN);
            i.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build());
            startActivityForResult(i, REQUEST_CODE);
        } else if (loginType == LoginType.PHONE) {
            Intent i = new Intent(activity, AccountKitActivity.class);
            AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                    new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE,
                            AccountKitActivity.ResponseType.TOKEN);
            configurationBuilder.setInitialPhoneNumber(new PhoneNumber("+84", numberPhone));
            i.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build());
            startActivityForResult(i, REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            AccountKitLoginResult accountKitLoginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (accountKitLoginResult.getError() != null) {
                Toast.makeText(activity, "" + accountKitLoginResult.getError().getErrorType().getMessage(), Toast.LENGTH_SHORT).show();
                return;
            } else if (accountKitLoginResult.wasCancelled()) {
                Toast.makeText(activity, "CANCEL", Toast.LENGTH_SHORT).show();
                return;
            } else {
                checkFirstLogin();
                if (accountKitLoginResult.getAccessToken() != null) {

                    Toast.makeText(activity, "Success! %s" + accountKitLoginResult.getAccessToken().getAccountId(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Success! %s" + accountKitLoginResult.getAuthorizationCode().substring(10, 0), Toast.LENGTH_SHORT).show();
                }
                LoginDialogFragment.this.dismiss();


            }
        }
    }


    private void checkFirstLogin() {
        mDataRef.orderByKey().equalTo(numberPhone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(activity, "co roi", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Khong Ton Tai", Toast.LENGTH_SHORT).show();
                    LoginDialogFragment.this.dismiss();
                    FirstUpdateInforFragment.newInstance().show(fragmentManager, Constant.FIRST_UPDATE_FRAGMENT);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(activity, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
