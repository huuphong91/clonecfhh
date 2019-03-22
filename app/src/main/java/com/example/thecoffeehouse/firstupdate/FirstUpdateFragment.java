package com.example.thecoffeehouse.firstupdate;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dx.dxloadingbutton.lib.LoadingButton;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.main.MainActivity;
import com.example.thecoffeehouse.main.OnUpdateListener;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class FirstUpdateFragment extends Fragment implements IFirstUpdateContract.View {

    private Toolbar toolbar;
    private AppCompatActivity activity;
    private TextInputEditText mEditTextLastName;
    private TextInputEditText mEditTextFirstName;
    private LoadingButton mButtonCommit;
    private String numberPhone;
    private ImageView mImageViewDelete;
    private FirstUpdatePresenter presenter;
    private OnUpdateListener onUpdateListener;

    public static FirstUpdateFragment newInstance() {
        FirstUpdateFragment fragment = new FirstUpdateFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnUpdateListener){
            onUpdateListener = (OnUpdateListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_update_infor, container, false);
        initView(view);
        getNumberPhone();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
    }

    @Override
    public void onResume() {
        super.onResume();
        initEvents();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initEvents() {
        mButtonCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonCommit.startLoading();
                presenter.insertUser(numberPhone,mEditTextFirstName.getText().toString(),mEditTextLastName.getText().toString());
            }
        });
        mImageViewDelete.setOnClickListener(v -> onUpdateListener.onUpdateFragment());
    }

    @Override
    public void insertUserSuccess(String messege) {
        mButtonCommit.loadingSuccessful();
        Intent newIntent = new Intent(activity,MainActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newIntent);
//        onUpdateListener.onUpdateFragment();
    }

    @Override
    public void insertUserFail(String messege) {
        mButtonCommit.loadingFailed();

    }

    @Override
    public Context activity() {
        return getContext();
    }

    private void getNumberPhone(){
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

    private void initView(View view){
        toolbar = view.findViewById(R.id.frag_update_toolbar);
        toolbar.setTitle("");
        activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        mEditTextFirstName = view.findViewById(R.id.frag_update_edt_firstname);
        mEditTextLastName = view.findViewById(R.id.frag_update_edt_lastname);
        mButtonCommit = view.findViewById(R.id.frag_update_btn_commit);
        mImageViewDelete = view.findViewById(R.id.frag_update_img_delete);
        presenter = new FirstUpdatePresenter(this);
    }

}
