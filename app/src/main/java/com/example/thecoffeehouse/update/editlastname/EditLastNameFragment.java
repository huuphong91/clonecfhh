package com.example.thecoffeehouse.update.editlastname;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.dx.dxloadingbutton.lib.LoadingButton;
import com.example.thecoffeehouse.Constant;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.main.OnUpdateListener;
import com.example.thecoffeehouse.update.UpdateFragment;

import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditLastNameFragment extends Fragment implements IEditLastNameContract.View {

    private IEditLastNameContract.Presenter presenter;
    private String mLastName;
    private String mNumberPhone;
    private ImageView mImageViewBack;
    private EditText mEditTextLastName;
    private LoadingButton mButtonCommit;
    private OnUpdateListener mListener;


    public static EditLastNameFragment newInstance(String numberPhone, String lastName) {
        EditLastNameFragment fragment = new EditLastNameFragment();
        Bundle bundle = new Bundle();
        bundle.putString("lastname", lastName);
        bundle.putString("numberphone", numberPhone);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Light_NoActionBar);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_last_name, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEvents();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            mLastName = getArguments().getString("lastname");
            mNumberPhone = getArguments().getString("numberphone");
        }
        if (context instanceof OnUpdateListener) {
            mListener = (OnUpdateListener) context;
        }
    }

    private void initView(View view) {
        mImageViewBack = view.findViewById(R.id.frag_editname_imgBack);
        mButtonCommit = view.findViewById(R.id.frag_editname_btnCommit);
        mEditTextLastName = view.findViewById(R.id.frag_editname_edtFirstName);
        mEditTextLastName.setText(mLastName);
        presenter = new EditLastNamePresenter(this);
    }

    private void initEvents() {
        mImageViewBack.setOnClickListener(v -> {
            mListener.onUpdateFragment();
        });

        mButtonCommit.setOnClickListener(v -> {
            mButtonCommit.startLoading();
            presenter.editLastName(mNumberPhone, mEditTextLastName.getText().toString());
        });
    }

    @Override
    public void onEditSuccess(String messege) {
        Log.d("onEditSuccess: ", "-----");
        mButtonCommit.loadingSuccessful();
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 mListener.onUpdateFragment();
             }
         }, 2000);


    }

    @Override
    public void onEditFail(String messege) {
        mButtonCommit.loadingFailed();
    }

    @Override
    public Context getContextt() {
        return getContext();
    }
}
