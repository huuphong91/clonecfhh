package com.example.thecoffeehouse.profile;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.thecoffeehouse.Constant;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.dialog.LoginDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ProfileFragment extends Fragment {

    private AppCompatActivity activity;
    private FirebaseAuth mAuth;
    private DatabaseReference mDataRef;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        final Toolbar toolbar = view.findViewById(R.id.frag_profile_toolbar);
        toolbar.setTitle("");
        activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        //      activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RelativeLayout mRel = toolbar.findViewById(R.id.frag_profile_tb_login);
        mAuth = FirebaseAuth.getInstance();
        mDataRef = FirebaseDatabase.getInstance().getReference();
        mRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginDialogFragment.newInstance().show(getFragmentManager(), Constant.LOGIN_FRAGMENT);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
        Log.d("onResume","FragProfile");
        Toast.makeText(activity, "OKE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("onPause","FragProfile");    }
}
