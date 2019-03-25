package com.example.thecoffeehouse.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.thecoffeehouse.Constant;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.user.User;
import com.example.thecoffeehouse.login.LoginDialogFragment;
import com.example.thecoffeehouse.main.FragmentInteractionListener;
import com.example.thecoffeehouse.main.OnUpdateListener;
import com.example.thecoffeehouse.update.UpdateFragment;
import com.facebook.accountkit.AccountKit;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    private AppCompatActivity activity;
    private FragmentInteractionListener mListener;
    private User mUser;
    private SharedPreferences mPrefs;
    private CircleImageView mImgeUser;
    private TextView mTextviewLogin;
    private RelativeLayout mLoginButton;
    private LinearLayout mLogoutButton;
    private Toolbar toolbar;
    private OnUpdateListener onUpdateListener;


    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        toolbar = view.findViewById(R.id.frag_profile_toolbar);
        toolbar.setTitle("");
        activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        //      activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mLoginButton = toolbar.findViewById(R.id.frag_profile_tb_login);
        mLogoutButton = view.findViewById(R.id.frag_profile_logout);
        mImgeUser = toolbar.findViewById(R.id.frag_profile_imgUser);
        mTextviewLogin = toolbar.findViewById(R.id.frag_profile_tvLogin);
        mPrefs = activity.getSharedPreferences("dataUser", Context.MODE_PRIVATE);
        checkUserLogin();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            mListener = (FragmentInteractionListener) context;
        }
        if (context instanceof OnUpdateListener) {
            onUpdateListener = (OnUpdateListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("onResume", "FragProfile");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("onPause", "FragProfile");
    }

    private void checkUserLogin() {
        Gson gson = new Gson();
        mUser = new User();
        String json = mPrefs.getString("myObject", null);
        mUser = gson.fromJson(json, User.class);
        if (mUser != null) {
            Log.d("checkUserLogin: ", "1" + mUser.getLastName());
            loggedView(mUser);
        } else {
            Log.d("checkUserLogin: ", "2");
            unLoginView();
        }
    }

    private void loggedView(User mUser) {
        mLogoutButton.setVisibility(View.VISIBLE);
        mImgeUser.setVisibility(View.VISIBLE);
        mTextviewLogin.setVisibility(View.VISIBLE);
        Glide.with(getActivity()).load(mUser.getImage()).placeholder(R.mipmap.store_placeholder).into(mImgeUser);
        mTextviewLogin.setText(mUser.getFirstName() + " " + mUser.getLastName());
        mTextviewLogin.setTypeface(null, Typeface.NORMAL);
        toolbar.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
        mTextviewLogin.setTextColor(Color.BLACK);
        mLoginButton.setOnClickListener(v -> mListener.onChangeFragment(UpdateFragment.newInstance(), Constant.UPDATE_FRAGMENT));
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mPrefs = getContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mPrefs.edit();
                editor.clear();
                editor.commit();
                AccountKit.logOut();
//                checkUserLogin();
                resetUI();
            }
        });


    }

    private void unLoginView() {
        mLogoutButton.setVisibility(View.GONE);
        mImgeUser.setVisibility(View.VISIBLE);
        mTextviewLogin.setVisibility(View.VISIBLE);
        mLoginButton.setOnClickListener(v -> mListener.onChangeFragment(LoginDialogFragment.newInstance(), Constant.LOGIN_FRAGMENT));

    }

    private void resetUI() {
        mLogoutButton.setVisibility(View.GONE);
        mTextviewLogin.setTypeface(null, Typeface.BOLD);
        mImgeUser.setImageResource(R.drawable.img_user_white);
        toolbar.setBackgroundColor(getContext().getResources().getColor(R.color.colorOrange));
        mTextviewLogin.setText(getContext().getResources().getString(R.string.label_login));
        mTextviewLogin.setTextColor(Color.WHITE);
        mLoginButton.setOnClickListener(v -> mListener.onChangeFragment(LoginDialogFragment.newInstance(), Constant.LOGIN_FRAGMENT));

    }
}
