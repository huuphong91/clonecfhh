package com.example.thecoffeehouse.update.editgender;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dx.dxloadingbutton.lib.LoadingButton;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.main.OnUpdateListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class EditGenderFragment extends Fragment implements IEditGenderContract.View {

    private IEditGenderContract.Presenter presenter;
    private TextView mTextViewMale;
    private TextView mTextViewFeMale;
    private LoadingButton mTextViewCancle;
    private String mNumberPhone;
    private OnUpdateListener mListener;


    public static EditGenderFragment newInstance(String numberPhone) {
        EditGenderFragment fragment = new EditGenderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("numberphone", numberPhone);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_gender, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEvents();
    }

    private void initView(View view) {
        mTextViewMale = view.findViewById(R.id.bottom_sheet_tvMale);
        mTextViewFeMale = view.findViewById(R.id.bottom_sheet_tvFemale);
        mTextViewCancle = view.findViewById(R.id.bottom_sheet_tvCancel);
        presenter = new EditGenderPresenter(this);
    }

    private void initEvents() {
        mTextViewMale.setOnClickListener(v -> {
            mTextViewCancle.startLoading();
            presenter.changeGender(mNumberPhone,mTextViewMale.getText().toString());
        });
        mTextViewFeMale.setOnClickListener(v -> {
            mTextViewCancle.startLoading();
            presenter.changeGender(mNumberPhone,mTextViewFeMale.getText().toString());
        });

        mTextViewCancle.setOnClickListener(v -> {
            mListener.onUpdateFragment();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        Window window = getActivity().getWindow();
//        window.setGravity(Gravity.BOTTOM);
//        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
//        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(params);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            mNumberPhone = getArguments().getString("numberphone");
        }
        if (context instanceof OnUpdateListener) {
            mListener = (OnUpdateListener) context;
        }
    }

    @Override
    public void onChangedSuccess(String messege) {
        mTextViewCancle.loadingSuccessful();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListener.onUpdateFragment();
            }
        }, 1000);
    }

    @Override
    public Context getContexttt() {
        return getContext();
    }


}
