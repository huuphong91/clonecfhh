package com.example.thecoffeehouse.update.editfirstname;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.os.Handler;
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
import com.example.thecoffeehouse.update.editlastname.EditLastNameFragment;
import com.example.thecoffeehouse.update.editlastname.EditLastNamePresenter;
import com.example.thecoffeehouse.update.editlastname.IEditLastNameContract;

import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditFirstNameFragment extends Fragment implements IEditFirstNameContract.View {

    private IEditFirstNameContract.Presenter presenter;
    private String mFirstName;
    private String mNumberPhone;
    private ImageView mImageViewBack;
    private EditText mEditTextLastName;
    private LoadingButton mButtonCommit;
    private OnUpdateListener mListener;

    public static EditFirstNameFragment newInstance(String numberPhone, String firstName) {
        EditFirstNameFragment fragment = new EditFirstNameFragment();
        Bundle bundle = new Bundle();
        bundle.putString("firstname", firstName);
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_first_name, container, false);
        initView(view);
        initEvents();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            mFirstName = getArguments().getString("firstname");
            mNumberPhone = getArguments().getString("numberphone");
        }
        if (context instanceof OnUpdateListener) {
            mListener = (OnUpdateListener) context;
        }
    }

    private void initView(View view) {
        mImageViewBack = view.findViewById(R.id.frag_editfirstname_imgBack);
        mButtonCommit = view.findViewById(R.id.frag_editfirstname_btnCommit);
        mEditTextLastName = view.findViewById(R.id.frag_editfirstname_edtFirstName);
        mEditTextLastName.setText(mFirstName);
        presenter = new EditFirstNamePresenter(this);
    }

    private void initEvents() {
        mImageViewBack.setOnClickListener(v -> {
//            UpdateFragment.newInstance().show(getFragmentManager(), Constant.UPDATE_FRAGMENT);
//            EditFirstNameFragment.this.dismiss();
            mListener.onUpdateFragment();
        });

        mButtonCommit.setOnClickListener(v -> {
            mButtonCommit.startLoading();
            presenter.editLastName(mNumberPhone, mEditTextLastName.getText().toString());
        });
    }

    @Override
    public void onEditSuccess(String messege) {
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

    }

    @Override
    public Context getContextt() {
        return getContext();
    }
}
