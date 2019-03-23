package com.example.thecoffeehouse.update.editbirthday;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dx.dxloadingbutton.lib.LoadingButton;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.main.OnUpdateListener;
import com.example.thecoffeehouse.update.editlastname.EditLastNameFragment;
import com.example.thecoffeehouse.update.editlastname.EditLastNamePresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditBirthDayFragment extends Fragment implements IEditBirhDayContract.View {

    EditBirthDayPresenter presenter;
    private String mNumberPhone;
    private String mBirthDay;
    private OnUpdateListener mListener;
    private ImageView mImageViewBack;
    private EditText mEditTextBirthDay;
    private LoadingButton mButtonCommit;

    public static EditBirthDayFragment newInstance(String numberPhone, String birthDay) {
        EditBirthDayFragment fragment = new EditBirthDayFragment();
        Bundle bundle = new Bundle();
        bundle.putString("birthday", birthDay);
        bundle.putString("numberphone", numberPhone);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_birth_day, container, false);
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
            mBirthDay = getArguments().getString("birthday");
            mNumberPhone = getArguments().getString("numberphone");
        }
        if (context instanceof OnUpdateListener) {
            mListener = (OnUpdateListener) context;
        }
    }

    private void initView(View view) {
        mImageViewBack = view.findViewById(R.id.frag_editbirthday_imgBack);
        mButtonCommit = view.findViewById(R.id.frag_editbirthday_btnCommit);
        mEditTextBirthDay = view.findViewById(R.id.frag_editbirthday_edtBirthDay);
        mEditTextBirthDay.setText(mBirthDay);
        presenter = new EditBirthDayPresenter(this);
    }

    private void initEvents(){
        mImageViewBack.setOnClickListener(v -> {
            mListener.onUpdateFragment();
        });

        mButtonCommit.setOnClickListener(v -> {
            mButtonCommit.startLoading();
            presenter.changeBirthDay(mNumberPhone, mEditTextBirthDay.getText().toString());
        });
    }

    @Override
    public void onChangeBirthdaySuccess(String messege) {
        Log.d("onEditSuccess: ", messege);
        mButtonCommit.loadingSuccessful();
        new Handler().postDelayed(() -> mListener.onUpdateFragment(), 2000);
    }

    @Override
    public void onChangeBirthdayFail(String messege) {
        mButtonCommit.loadingFailed();
        mButtonCommit.reset();
        Toast.makeText(getContext(), messege, Toast.LENGTH_SHORT).show();
    }


    @Override
    public Context getContextt() {
        return getContext();
    }

}
